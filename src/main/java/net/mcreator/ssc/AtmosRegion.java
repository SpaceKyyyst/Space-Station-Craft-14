package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import java.util.BitSet;
import java.util.ArrayList;
import java.util.List;

public final class AtmosRegion {
    private static final int REGION_SIZE = 16;
    private static final int CELL_COUNT = REGION_SIZE * REGION_SIZE * REGION_SIZE;
    private static final float VISUAL_THRESHOLD_MOLES = 0.05f;
    private static final int VISUAL_UPDATE_INTERVAL = 10;
    private static final int MAX_CELLS_PER_STEP = 256;

    private final int regionX, regionY, regionZ;
    private final AtmosCell[] cells = new AtmosCell[CELL_COUNT];
    private BitSet dirtyCells = new BitSet(CELL_COUNT);
    private final BitSet validPermeability = new BitSet(CELL_COUNT);
    private int workCursor;
    private final float[][] facePermeability = new float[6][CELL_COUNT];

    private ServerLevel level;

    private int visualTickCounter = 0;
    private final List<GasVisualData> pendingVisuals = new ArrayList<>();

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

    public AtmosRegion(int rx, int ry, int rz) {
        this.regionX = rx; this.regionY = ry; this.regionZ = rz;
    }

    public int packCellIndex(BlockPos pos) {
        int x = pos.getX() & 15, y = pos.getY() & 15, z = pos.getZ() & 15;
        return (y << 8) | (z << 4) | x;
    }

    public BlockPos unpackCellIndex(int index, BlockPos origin) {
        int x = index & 15, z = (index >> 4) & 15, y = (index >> 8) & 15;
        return origin.offset(x, y, z);
    }

    public BlockPos getRegionOrigin() {
        return new BlockPos(regionX << 4, regionY << 4, regionZ << 4);
    }

    private boolean isVentedToSpace(BlockPos pos) {
        if (level == null) return false;
        return (pos.getY() <= level.getMinY() || pos.getY() >= level.getMaxY() - 1)
                && getBlockPermeability(level.getBlockState(pos), pos) > 0;
    }

    public void updatePermeability(BlockPos pos) {
        if (level == null || !contains(pos)) return;
        int idx = packCellIndex(pos);
        validPermeability.clear(idx);
        dirtyCells.set(idx);
        for (int f = 0; f < 6; f++) {
            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            if (contains(nPos)) {
                validPermeability.clear(packCellIndex(nPos));
                dirtyCells.set(packCellIndex(nPos));
            }
        }
    }

    private void recalcCellFaces(BlockPos pos, int idx) {
        BlockState state = level.getBlockState(pos);
        float base = getBlockPermeability(state, pos);
        for (int f = 0; f < 6; f++) {
            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            float neighborPerm = level.hasChunkAt(nPos) ? getBlockPermeability(level.getBlockState(nPos), nPos) : 0;
            facePermeability[f][idx] = Math.min(base, neighborPerm);
        }
        validPermeability.set(idx);
    }

    private float getBlockPermeability(BlockState state, BlockPos pos) {
        if (state.getBlock() instanceof IAtmosBlock ab) {
            return ab.getPermeability(state);
        }
        if (state.is(IAtmosBlock.NON_HERMETIC_TAG)) return 1.0f;
        if (state.isAir()) return 1.0f;
        return state.getCollisionShape(level, pos).isEmpty() ? 1.0f : 0.0f;
    }

    public void initFromWorld(ServerLevel level) {
        this.level = level;
        validPermeability.clear();
    }

    public void initializeIfLoaded(ServerLevel serverLevel) {
        if (level == null && serverLevel.hasChunkAt(getRegionOrigin())) initFromWorld(serverLevel);
    }

    public void invalidatePermeability() {
        level = null;
        validPermeability.clear();
        for (int i = 0; i < CELL_COUNT; i++) {
            if (cells[i] != null && cells[i].getTotalMoles() > 0) dirtyCells.set(i);
        }
    }

    public List<GasVisualData> collectGasVisuals(BlockPos playerPos, int maxParticles) {
        pendingVisuals.clear();
        if (level == null || level.isClientSide()) return pendingVisuals;
        if (playerPos.distSqr(getRegionOrigin()) > 4096) return pendingVisuals;

        int spawned = 0;
        for (int i = 0; i < CELL_COUNT && spawned < maxParticles; i++) {
            AtmosCell cell = cells[i];
            if (cell == null) continue;
            for (int g = 0; g < GasType.COUNT; g++) {
                float moles = cell.getMoles(g);
                if (g >= GasType.PLASMA.ordinal() && moles > VISUAL_THRESHOLD_MOLES) {
                    BlockPos pos = unpackCellIndex(i, getRegionOrigin());
                    double px = pos.getX() + 0.5 + (level.getRandom().nextFloat() - 0.5f) * 0.3f;
                    double py = pos.getY() + 0.5 + (level.getRandom().nextFloat() - 0.5f) * 0.3f;
                    double pz = pos.getZ() + 0.5 + (level.getRandom().nextFloat() - 0.5f) * 0.3f;

                    pendingVisuals.add(new GasVisualData(px, py, pz, g, Math.min(moles * 2f, 1.0f)));
                    spawned++;
                    if (spawned >= maxParticles) break;
                }
            }
        }
        return pendingVisuals;
    }
    public void tick(float deltaTime, GasReaction[] reactions) {
        pendingVisuals.clear();

        if (dirtyCells.isEmpty()) return;

        BitSet pending = dirtyCells;
        dirtyCells = new BitSet(CELL_COUNT);
        for (int selected = 0; selected < MAX_CELLS_PER_STEP && !pending.isEmpty(); selected++) {
            int index = pending.nextSetBit(workCursor);
            if (index < 0) index = pending.nextSetBit(0);
            dirtyCells.set(index);
            pending.clear(index);
            workCursor = (index + 1) % CELL_COUNT;
        }
        BitSet nextDirty = pending;

        for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
            AtmosCell cell = cells[i];
            if (cell == null) continue;
            if (cell.getTotalMoles() <= 0f) continue;
            BlockPos pos = unpackCellIndex(i, getRegionOrigin());
            if (isVentedToSpace(pos)) {
                for (int g = 0; g < GasType.COUNT; g++) cell.setMoles(g, 0f);
                cell.setTemperature(294.15f);
                continue;
            }
        }

        for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
            if (cells[i] != null && cells[i].tryReactions(reactions)) {
                nextDirty.set(i);
            }
        }

        for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
            AtmosCell cell = cells[i];
            if (cell == null) continue;
            if (cell.getTotalMoles() <= 0f) continue;
            if (diffuseCell(i, deltaTime, nextDirty)) {
                nextDirty.set(i);
            }
        }

        dirtyCells = nextDirty;

        visualTickCounter++;
        if (visualTickCounter >= VISUAL_UPDATE_INTERVAL && level != null) {
            for (var player : level.players()) {
                if (player.blockPosition().distSqr(getRegionOrigin()) <= 4096) {
                    collectGasVisuals(player.blockPosition(), 32);
                    break;
                }
            }
            visualTickCounter = 0;
        }

    }

    private boolean diffuseCell(int idx, float dt, BitSet nextDirty) {
        AtmosCell cell = cells[idx];
        boolean changed = false;
        BlockPos pos = unpackCellIndex(idx, getRegionOrigin());
        if (level != null && !validPermeability.get(idx)) recalcCellFaces(pos, idx);

        for (int f = 0; f < 6; f++) {
            float perm = facePermeability[f][idx];
            if (perm <= 0.001f) continue;

            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            if (level != null && (nPos.getY() < level.getMinY() || nPos.getY() >= level.getMaxY()
                    || !level.hasChunkAt(nPos))) continue;
            AtmosCell neighbor = getCellAt(nPos);
            if (neighbor == null && level != null) neighbor = AtmosphereManager.get(level).getCellAt(nPos);
            if (neighbor == null) continue;

            float oldTemperature = neighbor.getTemperature();
            float transferred = cell.diffuseTo(neighbor, perm, dt);
            if (transferred > 0f || oldTemperature != neighbor.getTemperature()) {
                changed = true;
                if (contains(nPos)) nextDirty.set(packCellIndex(nPos));
                else if (level != null) AtmosphereManager.get(level).markCellDirty(nPos);
            }
        }
        return changed;
    }

    public AtmosCell getCellAt(BlockPos pos) {
        if ((pos.getX() >> 4) != regionX || (pos.getY() >> 4) != regionY || (pos.getZ() >> 4) != regionZ) return null;
        int index = packCellIndex(pos);
        if (cells[index] == null) cells[index] = new AtmosCell();
        return cells[index];
    }

    public AtmosCell peekCellAt(BlockPos pos) {
        return contains(pos) ? cells[packCellIndex(pos)] : null;
    }

    public boolean contains(BlockPos p) {
        return (p.getX() >> 4) == regionX && (p.getY() >> 4) == regionY && (p.getZ() >> 4) == regionZ;
    }

    public void markCellDirty(BlockPos pos) {
        if (!contains(pos)) return;
        int idx = packCellIndex(pos);
        dirtyCells.set(idx);
    }

    public boolean hasPendingWork() { return !dirtyCells.isEmpty(); }

    public boolean isActive() { return hasPendingWork(); }
    public List<GasVisualData> getPendingVisuals() { return pendingVisuals; }

    public void writeToNBT(net.minecraft.nbt.CompoundTag tag) {
        tag.putInt("x", regionX); tag.putInt("y", regionY); tag.putInt("z", regionZ);
        net.minecraft.nbt.ListTag cellsTag = new net.minecraft.nbt.ListTag();
        for (int i = 0; i < CELL_COUNT; i++) {
            if (cells[i] != null && (cells[i].getTotalMoles() > 0f || cells[i].getTemperature() != 294.15f)) {
                net.minecraft.nbt.CompoundTag cellTag = new net.minecraft.nbt.CompoundTag();
                cellTag.putInt("idx", i); cells[i].writeToNBT(cellTag); cellsTag.add(cellTag);
            }
        }
        tag.put("cells", cellsTag);
    }

    public void readFromNBT(net.minecraft.nbt.CompoundTag tag) {
        var entries = tag.getList("cells");
        if (entries.isEmpty()) return;
        var origin = getRegionOrigin();
        for (int entryIndex = 0; entryIndex < entries.get().size(); entryIndex++) {
            var entry = entries.get().getCompound(entryIndex);
            if (entry.isEmpty()) continue;
            int cellIndex = entry.get().getInt("idx").orElse(-1);
            if (cellIndex < 0 || cellIndex >= CELL_COUNT) continue;
            if (cells[cellIndex] == null) cells[cellIndex] = new AtmosCell();
            cells[cellIndex].readFromNBT(entry.get());
            markCellDirty(unpackCellIndex(cellIndex, origin));
        }
    }
}
