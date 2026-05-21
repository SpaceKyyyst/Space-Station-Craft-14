
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
    private static final long ACTIVITY_TIMEOUT_MS = 10000;
    private static final int MAX_DIRTY_CELLS = 2048;
    private static final float MIN_TOTAL_MOLES_TO_TICK = 0.1f;
    private static final float PLASMA_VISUAL_THRESHOLD = 0.05f;
    private static final int VISUAL_UPDATE_INTERVAL = 10;
    private static final int VENT_SCAN_RANGE = 20; // 🔍 Радиус проверки на "выход в космос"

    private final int regionX, regionY, regionZ;
    private final AtmosCell[] cells = new AtmosCell[CELL_COUNT];
    private BitSet dirtyCells = new BitSet(CELL_COUNT);
    private final BitSet reactionCells = new BitSet(CELL_COUNT);
    private final float[][] facePermeability = new float[6][CELL_COUNT];

    public boolean isActive = false;
    public long lastActivityTime = 0;
    private GasReaction[] reactions;
    private ServerLevel level;
    
    private int visualTickCounter = 0;
    private final List<PlasmaVisualData> pendingVisuals = new ArrayList<>();

    public static class PlasmaVisualData {
        public final double x, y, z;
        public final float density;
        public PlasmaVisualData(double x, double y, double z, float density) {
            this.x = x; this.y = y; this.z = z; this.density = density;
        }
    }
    
	// 📦 Данные для визуализации водяного пара
	public static class VaporVisualData {
	    public final double x, y, z;
	    public final float density;
	    public VaporVisualData(double x, double y, double z, float density) {
	        this.x = x; this.y = y; this.z = z; this.density = density;
	    }
	}

    public AtmosRegion(int rx, int ry, int rz) {
        this.regionX = rx; this.regionY = ry; this.regionZ = rz;
        for (int i = 0; i < CELL_COUNT; i++) cells[i] = new AtmosCell();
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

    // 🔥 ИСПРАВЛЕННЫЙ МЕТОД: проверка на "выход в космос" в радиусе 20 блоков
    private boolean isVentedToSpace(BlockPos pos) {
        if (level == null) return false;
        
        // 🔼 Проверка ВВЕРХ: если на расстоянии до 20 блоков нет полных блоков → космос
        for (int dy = 1; dy <= VENT_SCAN_RANGE; dy++) {
            BlockPos check = pos.above(dy);
            BlockState state = level.getBlockState(check);
            // Если блок имеет коллизию (не воздух, не стекло, не решётка) — путь закрыт
            if (!state.isAir() && !state.getCollisionShape(level, check).isEmpty()) {
                break; // Уперлись в твёрдый блок
            }
            // Если достигли границы мира — гарантированный космос
            if (check.getY() >= level.getMaxY()) return true;
        }
        
        // 🔽 Проверка ВНИЗ: аналогично
        for (int dy = 1; dy <= VENT_SCAN_RANGE; dy++) {
            BlockPos check = pos.below(dy);
            BlockState state = level.getBlockState(check);
            if (!state.isAir() && !state.getCollisionShape(level, check).isEmpty()) {
                break;
            }
            if (check.getY() < level.getMinY()) return true;
        }
        
        return false;
    }

    // ✅ ИСПРАВЛЕННЫЙ МЕТОД: проницаемость с учётом реальной позиции блока
    private float getBlockPermeability(BlockState state, BlockPos pos) {
        // 1. Проверяем интерфейс IAtmosBlock (кастомные блоки мода)
        if (state.getBlock() instanceof IAtmosBlock ab) {
            return ab.getPermeability(state);
        }
        
        // 2. Проверяем тег "негерметичный" (для совместимости с другими модами)
        // Создаём тег динамически, если не зарегистрирован
        try {
			var tagKey = net.minecraft.tags.BlockTags.create(
			    net.minecraft.resources.ResourceLocation.parse("ssc_14:non_hermetic"));
            if (state.is(tagKey)) return 1.0f;
        } catch (Exception ignored) {}
        
        // 3. Воздух — полностью проницаем
        if (state.isAir()) return 1.0f;
        
        // 4. Неполные блоки (без полной коллизии) — проницаемы
        // ✅ КРИТИЧЕСКИЙ ФИКС: используем реальную позицию pos вместо BlockPos.ZERO!
        return state.getCollisionShape(level, pos).isEmpty() ? 1.0f : 0.0f;
    }

    public void updatePermeability(BlockPos pos) {
        if (level == null || !contains(pos)) return;
        int idx = packCellIndex(pos);
        recalcCellFaces(pos, idx);
        dirtyCells.set(idx);
        
        // Граница зависит от обоих блоков, поэтому обновляем 6 соседей
        for (int f = 0; f < 6; f++) {
            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            if (contains(nPos)) {
                recalcCellFaces(nPos, packCellIndex(nPos));
                dirtyCells.set(packCellIndex(nPos));
            }
        }
        isActive = true;
        lastActivityTime = System.currentTimeMillis();
    }

    private void recalcCellFaces(BlockPos pos, int idx) {
        BlockState state = level.getBlockState(pos);
        // ✅ Передаём реальную позицию для корректной коллизии
        float base = getBlockPermeability(state, pos);
        for (int f = 0; f < 6; f++) {
            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            float neighborPerm = getBlockPermeability(level.getBlockState(nPos), nPos);
            // Граница герметична, если хоть одна сторона герметична
            facePermeability[f][idx] = Math.min(base, neighborPerm);
        }
    }

    public void initFromWorld(ServerLevel level) {
        this.level = level;
        BlockPos origin = getRegionOrigin();
        for (int y = 0; y < REGION_SIZE; y++)
            for (int z = 0; z < REGION_SIZE; z++)
                for (int x = 0; x < REGION_SIZE; x++) {
                    int idx = (y << 8) | (z << 4) | x;
                    BlockPos pos = origin.offset(x, y, z);
                    recalcCellFaces(pos, idx);
                    // ❌ НЕ помечаем как грязные — пустой регион не должен тикаться
                }
    }

    // 🔥 Получение данных для визуализации плазмы
    public List<PlasmaVisualData> collectPlasmaVisuals(BlockPos playerPos, int maxParticles) {
        pendingVisuals.clear();
        if (level == null || level.isClientSide) return pendingVisuals;
        if (playerPos.distSqr(getRegionOrigin()) > 4096) return pendingVisuals;
        
        int spawned = 0;
        for (int i = 0; i < CELL_COUNT && spawned < maxParticles; i++) {
            AtmosCell cell = cells[i];
            float plasma = cell.getMoles(GasType.PLASMA.ordinal());
            if (plasma > PLASMA_VISUAL_THRESHOLD) {
                BlockPos pos = unpackCellIndex(i, getRegionOrigin());
                double px = pos.getX() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
                double py = pos.getY() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
                double pz = pos.getZ() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
                pendingVisuals.add(new PlasmaVisualData(px, py, pz, Math.min(plasma * 2f, 1.0f)));
                spawned++;
            }
        }
        return pendingVisuals;
    }

	// 🔥 Получение данных для визуализации водяного пара
	public List<VaporVisualData> collectVaporVisuals(BlockPos playerPos, int maxParticles) {
	    List<VaporVisualData> pending = new ArrayList<>();
	    if (level == null || level.isClientSide) return pending;
	    if (playerPos.distSqr(getRegionOrigin()) > 4096) return pending;
	    
	    int spawned = 0;
	    for (int i = 0; i < CELL_COUNT && spawned < maxParticles; i++) {
	        AtmosCell cell = cells[i];
	        // ✅ Убедитесь, что GasType.WATER_VAPOR существует!
	        float vapor = cell.getMoles(GasType.WATER_VAPOR.ordinal());
	        
	        if (vapor > 0.05f) { // Порог видимости пара
	            BlockPos pos = unpackCellIndex(i, getRegionOrigin());
	            double px = pos.getX() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
	            double py = pos.getY() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
	            double pz = pos.getZ() + 0.5 + (level.random.nextFloat() - 0.5f) * 0.3f;
	            
	            pending.add(new VaporVisualData(px, py, pz, Math.min(vapor * 2f, 1.0f)));
	            spawned++;
	        }
	    }
	    return pending;
	}

    public void tick(float deltaTime, GasReaction[] reactions) {
        this.reactions = reactions;
        
        if (dirtyCells.isEmpty()) {
            if (isActive && System.currentTimeMillis() - lastActivityTime > ACTIVITY_TIMEOUT_MS) {
                isActive = false;
            }
            return;
        }

        // 🔒 Обрезка списка грязных ячеек если их слишком много
        int dirtyCount = dirtyCells.cardinality();
        if (dirtyCount > MAX_DIRTY_CELLS) {
            BitSet trimmed = new BitSet(CELL_COUNT);
            int kept = 0;
            for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
                if (cells[i].getTotalMoles() > 0.01f || kept < 512) {
                    trimmed.set(i);
                    kept++;
                }
            }
            dirtyCells = trimmed;
        }

        // 🚫 Ранний выход если газа почти нет
        float totalMoles = 0f;
        for (int i = 0; i < CELL_COUNT; i++) totalMoles += cells[i].getTotalMoles();
        if (totalMoles < MIN_TOTAL_MOLES_TO_TICK) {
            if (isActive && System.currentTimeMillis() - lastActivityTime > ACTIVITY_TIMEOUT_MS) {
                isActive = false;
            }
            return;
        }

        boolean anyChanged = false;
        BitSet nextDirty = new BitSet(CELL_COUNT);

        // 🔥 ШАГ 1: МГНОВЕННОЕ УДАЛЕНИЕ ГАЗА В КОСМОС (ДО диффузии!)
        for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
            AtmosCell cell = cells[i];
            if (cell.getTotalMoles() <= 0.001f) continue;
            
            BlockPos pos = unpackCellIndex(i, getRegionOrigin());
            if (isVentedToSpace(pos)) {
                // ✅ Мгновенное удаление всего газа
                for (int g = 0; g < GasType.COUNT; g++) cell.setMoles(g, 0f);
                cell.setTemperature(294.15f);
                anyChanged = true;
                // ❌ НЕ добавляем в nextDirty — ячейка становится "спящей"
                continue;
            }
        }

        // 🔥 ШАГ 2: Химические реакции
        for (int i = reactionCells.nextSetBit(0); i >= 0; i = reactionCells.nextSetBit(i + 1)) {
            if (cells[i].tryReactions(reactions)) {
                anyChanged = true;
                markNeighborsDirty(i);
            }
        }
        reactionCells.clear();

        // 🔥 ШАГ 3: Диффузия
        for (int i = dirtyCells.nextSetBit(0); i >= 0; i = dirtyCells.nextSetBit(i + 1)) {
            AtmosCell cell = cells[i];
            if (cell.getTotalMoles() <= 0.001f) continue;
            
            if (diffuseCell(i, deltaTime, nextDirty)) anyChanged = true;
            
            if (cell.getTotalMoles() > 0.005f || cell.getPressure() > 5f) {
                nextDirty.set(i);
            }
        }

        dirtyCells = nextDirty;
        
        // 🔥 ШАГ 4: Сбор данных для визуализации
        visualTickCounter++;
        if (visualTickCounter >= VISUAL_UPDATE_INTERVAL && isActive) {
            collectPlasmaVisuals(level.players().isEmpty() ? getRegionOrigin() : 
                level.players().iterator().next().blockPosition(), 32);
            visualTickCounter = 0;
        }
        
        if (anyChanged) {
            isActive = true;
            lastActivityTime = System.currentTimeMillis();
        }
    }

    private boolean diffuseCell(int idx, float dt, BitSet nextDirty) {
        AtmosCell cell = cells[idx];
        boolean changed = false;
        BlockPos pos = unpackCellIndex(idx, getRegionOrigin());

        for (int f = 0; f < 6; f++) {
            float perm = facePermeability[f][idx];
            if (perm <= 0.001f) continue;

            BlockPos nPos = pos.relative(net.minecraft.core.Direction.from3DDataValue(f));
            AtmosCell neighbor = getCellAt(nPos);
            if (neighbor == null && level != null) neighbor = AtmosphereManager.get(level).getCellAt(nPos);
            if (neighbor == null) continue;

            float transferred = cell.diffuseTo(neighbor, perm, dt);
            if (transferred > 1e-5f) {
                changed = true;
                if (contains(nPos)) nextDirty.set(packCellIndex(nPos));
            }
        }
        return changed;
    }

    public AtmosCell getCellAt(BlockPos pos) {
        if ((pos.getX() >> 4) != regionX || (pos.getY() >> 4) != regionY || (pos.getZ() >> 4) != regionZ) return null;
        return cells[packCellIndex(pos)];
    }

    public boolean contains(BlockPos p) {
        return (p.getX() >> 4) == regionX && (p.getY() >> 4) == regionY && (p.getZ() >> 4) == regionZ;
    }

    private void markNeighborsDirty(int idx) {
        BlockPos p = unpackCellIndex(idx, getRegionOrigin());
        for (int f = 0; f < 6; f++) {
            BlockPos n = p.relative(net.minecraft.core.Direction.from3DDataValue(f));
            if (contains(n)) dirtyCells.set(packCellIndex(n));
        }
    }

    public void markCellDirty(BlockPos pos) {
        if (!contains(pos)) return;
        int idx = packCellIndex(pos);
        dirtyCells.set(idx);
        isActive = true;
        lastActivityTime = System.currentTimeMillis();
    }

    public boolean isActive() { return isActive; }
    public List<PlasmaVisualData> getPendingVisuals() { return pendingVisuals; }
    public List<VaporVisualData> getPendingVaporVisuals() { 
	    return collectVaporVisuals(
	        level.players().isEmpty() ? getRegionOrigin() : level.players().iterator().next().blockPosition(), 
	        32
	    ); 
	}

    // === NBT ===
    public void writeToNBT(net.minecraft.nbt.CompoundTag tag) {
        tag.putInt("x", regionX); tag.putInt("y", regionY); tag.putInt("z", regionZ);
        net.minecraft.nbt.ListTag cellsTag = new net.minecraft.nbt.ListTag();
        for (int i = 0; i < CELL_COUNT; i++) {
            if (cells[i].getTotalMoles() > 0.01f) {
                net.minecraft.nbt.CompoundTag cellTag = new net.minecraft.nbt.CompoundTag();
                cellTag.putInt("idx", i); cells[i].writeToNBT(cellTag); cellsTag.add(cellTag);
            }
        }
        tag.put("cells", cellsTag);
    }

    public void readFromNBT(net.minecraft.nbt.CompoundTag tag) {
        if (!tag.contains("cells")) return;
        try {
            var opt = tag.getList("cells");
            if (opt.isEmpty()) return;
            net.minecraft.nbt.ListTag cellsTag = opt.get();
            for (int i = 0; i < cellsTag.size(); i++) {
                var cOpt = cellsTag.getCompound(i); if (cOpt.isEmpty()) continue;
                var c = cOpt.get(); var iOpt = c.getInt("idx"); if (iOpt.isEmpty()) continue;
                int idx = iOpt.get();
                if (idx >= 0 && idx < CELL_COUNT) { cells[idx].readFromNBT(c); dirtyCells.set(idx); }
            }
        } catch (Exception ignored) {}
    }
}
