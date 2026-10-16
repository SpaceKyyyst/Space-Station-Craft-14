package net.mcreator.ssc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import net.mcreator.ssc.atmosphere.GasMixture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;

/** Sparse section storage and bounded work scheduling; gas arithmetic lives in AtmosCell. */
public final class AtmosRegion {
    private static final int REGION_SIZE = 16;
    private static final int CELL_COUNT = REGION_SIZE * REGION_SIZE * REGION_SIZE;
    private static final int MAX_DIRTY_CELLS = 256;
    private static final int VENT_SCAN_RANGE = 20;
    private static final float VISUAL_THRESHOLD = 0.05f;
    private final int regionX, regionY, regionZ;
    private final AtmosCell[] cells = new AtmosCell[CELL_COUNT];
    private final BitSet dirtyCells = new BitSet(CELL_COUNT);
    private final Runnable changed;
    private final List<GasVisualData> pendingVisuals = new ArrayList<>();
    private ServerLevel level;
    private int nextCell;
    public boolean isActive;
    public long lastActivityTime;

    public static class GasVisualData {
        public final double x, y, z;
        public final int gasIndex;
        public final float density;
        public GasVisualData(double x, double y, double z, int gasIndex, float density) {
            this.x = x; this.y = y; this.z = z;
            this.gasIndex = gasIndex;
            this.density = density;
        }
    }

    public AtmosRegion(int rx, int ry, int rz) { this(rx, ry, rz, () -> { }); }

    AtmosRegion(int rx, int ry, int rz, Runnable changed) {
        regionX = rx; regionY = ry; regionZ = rz;
        this.changed = changed;
    }

    public int packCellIndex(BlockPos pos) {
        return ((pos.getY() & 15) << 8) | ((pos.getZ() & 15) << 4) | (pos.getX() & 15);
    }

    public BlockPos unpackCellIndex(int index, BlockPos origin) {
        return origin.offset(index & 15, (index >> 8) & 15, (index >> 4) & 15);
    }

    public BlockPos getRegionOrigin() { return new BlockPos(regionX << 4, regionY << 4, regionZ << 4); }

    private AtmosCell cell(int index) {
        if (cells[index] == null) cells[index] = new AtmosCell(() -> markDirty(index));
        return cells[index];
    }

    private void markDirty(int index) {
        dirtyCells.set(index);
        isActive = true;
        lastActivityTime = System.currentTimeMillis();
        changed.run();
    }

    private boolean isVentedToSpace(BlockPos pos) {
        if (level == null || AtmosBlockRegistry.getPermeability(level, pos) <= 0) return false;
        for (Direction direction : new Direction[]{Direction.UP, Direction.DOWN}) {
            for (int distance = 1; distance <= VENT_SCAN_RANGE; distance++) {
                BlockPos check = pos.relative(direction, distance);
                if (level.isOutsideBuildHeight(check)) return true;
                if (AtmosBlockRegistry.getPermeability(level, check) <= 0) break;
            }
        }
        return false;
    }

    /** Faces are sampled at diffusion time, so door state and cancelled events cannot stale a cache. */
    public void updatePermeability(BlockPos pos) {
        if (!contains(pos)) return;
        markCellDirty(pos);
        for (Direction direction : Direction.values()) markCellDirty(pos.relative(direction));
    }

    public void initFromWorld(ServerLevel level) { this.level = level; }

    public List<GasVisualData> collectGasVisuals(BlockPos playerPos, int maxParticles) {
        pendingVisuals.clear();
        if (level == null || maxParticles <= 0) return List.of();
        BlockPos origin = getRegionOrigin();
        if (playerPos.distSqr(origin) > 96 * 96) return List.of();
        for (int i = 0; i < CELL_COUNT && pendingVisuals.size() < maxParticles; i++) {
            AtmosCell cell = cells[i];
            if (cell == null) continue;
            BlockPos pos = unpackCellIndex(i, origin);
            if (playerPos.distSqr(pos) > 64 * 64) continue;
            for (int gas = 0; gas < GasType.COUNT && pendingVisuals.size() < maxParticles; gas++) {
                // Oxygen, nitrogen, carbon dioxide and nitrous oxide have no particle registrations.
                if (gas < GasType.PLASMA.ordinal()) continue;
                float moles = cell.getMoles(gas);
                if (moles > VISUAL_THRESHOLD) {
                    pendingVisuals.add(new GasVisualData(pos.getX() + 0.5, pos.getY() + 0.5,
                            pos.getZ() + 0.5, gas, Math.min(moles * 2f, 1f)));
                }
            }
        }
        return List.copyOf(pendingVisuals);
    }

    public void tick(float deltaTime, GasReaction[] reactions) {
        pendingVisuals.clear();
        if (!Float.isFinite(deltaTime) || deltaTime <= 0) return;
        BitSet work = (BitSet) dirtyCells.clone();
        int processed = 0;
        int index = work.nextSetBit(nextCell);
        if (index < 0) index = work.nextSetBit(0);
        while (index >= 0 && processed < MAX_DIRTY_CELLS) {
            work.clear(index);
            dirtyCells.clear(index);
            AtmosCell cell = cells[index];
            if (cell != null && cell.getTotalMoles() > 0) {
                BlockPos pos = unpackCellIndex(index, getRegionOrigin());
                if (isVentedToSpace(pos)) {
                    for (int gas = 0; gas < GasType.COUNT; gas++) cell.setMoles(gas, 0f);
                    cell.setTemperature(GasMixture.DEFAULT_TEMPERATURE_K);
                } else {
                    cell.tryReactions(reactions);
                    diffuseCell(index, deltaTime);
                }
            }
            nextCell = (index + 1) % CELL_COUNT;
            processed++;
            index = work.nextSetBit(nextCell);
            if (index < 0) index = work.nextSetBit(0);
        }
        isActive = !dirtyCells.isEmpty();
    }

    private void diffuseCell(int index, float seconds) {
        if (level == null) return;
        BlockPos pos = unpackCellIndex(index, getRegionOrigin());
        float sourcePermeability = AtmosBlockRegistry.getPermeability(level, pos);
        if (sourcePermeability <= 0) return;
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            float permeability = Math.min(sourcePermeability, AtmosBlockRegistry.getPermeability(level, neighborPos));
            if (permeability <= 0) continue;
            AtmosCell neighbor = contains(neighborPos) ? getCellAt(neighborPos)
                    : AtmosphereManager.get(level).getCellAt(neighborPos);
            cells[index].diffuseTo(neighbor, permeability, seconds);
        }
    }

    public AtmosCell peekCellAt(BlockPos pos) { return contains(pos) ? cells[packCellIndex(pos)] : null; }

    public AtmosCell getCellAt(BlockPos pos) { return contains(pos) ? cell(packCellIndex(pos)) : null; }

    public boolean contains(BlockPos pos) {
        return (pos.getX() >> 4) == regionX && (pos.getY() >> 4) == regionY && (pos.getZ() >> 4) == regionZ;
    }

    public void markCellDirty(BlockPos pos) {
        if (contains(pos)) markDirty(packCellIndex(pos));
    }

    /** Recheck previously sleeping gas when an adjacent chunk becomes available. */
    void wakeGasCells() {
        for (int i = 0; i < CELL_COUNT; i++) {
            if (cells[i] != null && cells[i].getTotalMoles() > 0) markDirty(i);
        }
    }

    public boolean isActive() { return isActive; }
    public List<GasVisualData> getPendingVisuals() { return List.copyOf(pendingVisuals); }

    public void writeToNBT(CompoundTag tag) {
        tag.putInt("x", regionX); tag.putInt("y", regionY); tag.putInt("z", regionZ);
        ListTag savedCells = new ListTag();
        for (int i = 0; i < CELL_COUNT; i++) {
            AtmosCell cell = cells[i];
            if (cell != null && (cell.getTotalMoles() > 0 || cell.getTemperature() != GasMixture.DEFAULT_TEMPERATURE_K)) {
                CompoundTag saved = new CompoundTag();
                saved.putInt("idx", i);
                cell.writeToNBT(saved);
                savedCells.add(saved);
            }
        }
        tag.put("cells", savedCells);
    }

    public void readFromNBT(CompoundTag tag) {
        java.util.Arrays.fill(cells, null);
        dirtyCells.clear();
        pendingVisuals.clear();
        isActive = false;
        ListTag savedCells = tag.getList("cells").orElseGet(ListTag::new);
        for (int i = 0; i < savedCells.size(); i++) {
            CompoundTag saved = savedCells.getCompound(i).orElse(null);
            if (saved == null) continue;
            int index = saved.getInt("idx").orElse(-1);
            if (index >= 0 && index < CELL_COUNT) cell(index).readFromNBT(saved);
        }
    }
}
