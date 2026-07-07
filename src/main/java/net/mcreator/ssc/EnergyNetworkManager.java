package net.mcreator.ssc;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.block.SheathingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;

public class EnergyNetworkManager {
    private static final Map<LevelAccessor, Map<CableType, List<EnergyNetwork>>> WORLD_NETWORKS = new WeakHashMap<>();
    private static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    public static List<EnergyNetwork> getNetworks(LevelAccessor level, CableType type) {
        return WORLD_NETWORKS
                .computeIfAbsent(level, k -> new EnumMap<>(CableType.class))
                .computeIfAbsent(type, k -> new ArrayList<>());
    }

    public static void updatePosition(LevelAccessor level, BlockPos pos) {
        if (level == null || level.isClientSide()) return;

        System.out.println("=== [SSC14-GRAF] Умный пересчёт в точке: " + pos + " ===");
        BlockState triggerState = level.getBlockState(pos);

        // СЛУЧАЙ 1: Кликнули/обновили сам кабель
        if (triggerState.getBlock() instanceof SheathingBlock) {
            for (CableType type : CableType.values()) {
                rebuildNetworksForType(level, pos, type);
            }
            return;
        }

        // СЛУЧАЙ 2: Обновился прибор (Лампа, ЛКП, Подстанция, Генератор) на высоте.
        // Реализуем "Обратный умный скан" строго вниз согласно техническому паспорту геометрии.
        boolean handledByDeviceScan = false;

        // А. Проверка Лампы (LV потребитель). Ищет кабель строго вниз от 1 до 6 блоков.
        if (triggerState.is(Ssc14ModBlocks.LAMP.get())) {
            for (int yOffset = 1; yOffset <= 6; yOffset++) {
                BlockPos targetCablePos = pos.below(yOffset);
                BlockState cableState = level.getBlockState(targetCablePos);
                if (cableState.getBlock() instanceof SheathingBlock && CableType.LV.hasCable(cableState)) {
                    // Лампа также проверяет куб 5x5 по горизонтали. 
                    // Проверяем, попадает ли кабель в горизонтальный радиус от прибора (в пределах 2 блоков)
                    rebuildNetworksForType(level, targetCablePos, CableType.LV);
                    handledByDeviceScan = true;
                }
            }
        }
        // Б. Проверка ЛКП / APC (LV источник, MV потребитель). Ищет кабель строго вниз от 1 до 5 блоков.
        else if (triggerState.is(Ssc14ModBlocks.APC.get())) {
            for (int yOffset = 1; yOffset <= 5; yOffset++) {
                BlockPos targetCablePos = pos.below(yOffset);
                BlockState cableState = level.getBlockState(targetCablePos);
                if (cableState.getBlock() instanceof SheathingBlock) {
                    if (CableType.LV.hasCable(cableState)) rebuildNetworksForType(level, targetCablePos, CableType.LV);
                    if (CableType.MV.hasCable(cableState)) rebuildNetworksForType(level, targetCablePos, CableType.MV);
                    handledByDeviceScan = true;
                }
            }
        }
        // В. Проверка Подстанции (MV источник, HV потребитель). Ищет кабель строго вниз на 1 или 2 блока.
        else if (triggerState.is(Ssc14ModBlocks.PODSTATION.get())) {
            for (int yOffset = 1; yOffset <= 2; yOffset++) {
                BlockPos targetCablePos = pos.below(yOffset);
                BlockState cableState = level.getBlockState(targetCablePos);
                if (cableState.getBlock() instanceof SheathingBlock) {
                    if (CableType.MV.hasCable(cableState)) rebuildNetworksForType(level, targetCablePos, CableType.MV);
                    if (CableType.HV.hasCable(cableState)) rebuildNetworksForType(level, targetCablePos, CableType.HV);
                    handledByDeviceScan = true;
                }
            }
        }
        // Г. Проверка Генератора (HV источник). Ищет кабель строго вниз на 1 или 2 блока.
        else if (triggerState.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
            for (int yOffset = 1; yOffset <= 2; yOffset++) {
                BlockPos targetCablePos = pos.below(yOffset);
                BlockState cableState = level.getBlockState(targetCablePos);
                if (cableState.getBlock() instanceof SheathingBlock && CableType.HV.hasCable(cableState)) {
                    rebuildNetworksForType(level, targetCablePos, CableType.HV);
                    handledByDeviceScan = true;
                }
            }
        }

        // СЛУЧАЙ 3: Базовая логика для разрушенных блоков или обычных блоков среды.
        // Зачищаем точку из графов и проверяем горизонтальных соседей, ТОЛЬКО если это не был наш прибор.
        if (!handledByDeviceScan) {
            for (CableType type : CableType.values()) {
                List<EnergyNetwork> networks = getNetworks(level, type);
                
                for (EnergyNetwork net : networks) {
                    net.getCables().remove(pos);
                    net.getSources().remove(pos);
                    net.getConsumers().remove(pos);
                }
                networks.removeIf(net -> net.getCables().isEmpty());

                for (Direction dir : HORIZONTAL_DIRECTIONS) {
                    BlockPos neighborPos = pos.relative(dir);
                    if (level.getBlockState(neighborPos).getBlock() instanceof SheathingBlock) {
                        rebuildNetworksForType(level, neighborPos, type);
                    }
                }
            }
        }
    }

    private static void rebuildNetworksForType(LevelAccessor level, BlockPos startPos, CableType type) {
        List<EnergyNetwork> networks = getNetworks(level, type);
        BlockState currentState = level.getBlockState(startPos);

        if (!(currentState.getBlock() instanceof SheathingBlock) || !type.hasCable(currentState)) {
            for (EnergyNetwork net : networks) {
                net.getCables().remove(startPos);
                net.getSources().remove(startPos);
                net.getConsumers().remove(startPos);
            }
            networks.removeIf(net -> net.getCables().isEmpty());
            return;
        }

		// === КРИТИЧЕСКОЕ ИСПРАВЛЕНИЕ ДУБЛИРОВАНИЯ СЕТЕЙ ===
		// Мы должны удалить не только сеть стартовой точки, но и ЛЮБУЮ сеть,
		// которая касается хотя бы одного из четырёх соседей этого кабеля!
		Set<BlockPos> pointsToClear = new HashSet<>();
		pointsToClear.add(startPos.immutable());
		for (Direction dir : HORIZONTAL_DIRECTIONS) {
			pointsToClear.add(startPos.relative(dir).immutable());
		}
		networks.removeIf(net -> net.getCables().stream().anyMatch(pointsToClear::contains));
		// =================================================

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos.immutable());
        visited.add(startPos.immutable());

        EnergyNetwork newNetwork = new EnergyNetwork(type);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            mapCableToNetwork(level, newNetwork, current);

            for (Direction dir : HORIZONTAL_DIRECTIONS) {
                BlockPos next = current.relative(dir);
                if (!visited.contains(next) && level.getBlockState(next).getBlock() instanceof SheathingBlock) {
                    BlockState nextState = level.getBlockState(next);
                    if (type.hasCable(nextState)) {
                        queue.add(next.immutable());
                        visited.add(next.immutable());
                    }
                }
            }
        }

        if (!newNetwork.getCables().isEmpty()) {
            networks.add(newNetwork);
            System.out.println("[SSC14-GRAF] Успешно объединено. Сеть [" + type + "]. Кабелей: " + newNetwork.getCableCount() + ", Ламп/Приборов: " + newNetwork.getConsumers().size());
        }
    }

    private static void mapCableToNetwork(LevelAccessor level, EnergyNetwork network, BlockPos cablePos) {
        network.addCable(cablePos);

        if (network.getType() == CableType.LV) {
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.APC.get())) {
                    network.getSources().add(checkPos.immutable());
                    break; 
                }
            }

            for (int yOffset = 1; yOffset <= 6; yOffset++) {
                for (int xOffset = -2; xOffset <= 2; xOffset++) {
                    for (int zOffset = -2; zOffset <= 2; zOffset++) {
                        BlockPos checkPos = cablePos.offset(xOffset, yOffset, zOffset);
                        BlockState state = level.getBlockState(checkPos);
                        if (state.is(Ssc14ModBlocks.LAMP.get())) {
                            network.getConsumers().add(checkPos.immutable());
                        }
                    }
                }
            }
        } 
        else if (network.getType() == CableType.HV) {
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = cablePos.above(y);
                BlockState state = level.getBlockState(checkPos);
                if (state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                    network.getSources().add(checkPos.immutable());
                } else if (state.is(Ssc14ModBlocks.PODSTATION.get())) {
                    network.getConsumers().add(checkPos.immutable());
                }
            }
        } 
        else if (network.getType() == CableType.MV) {
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.PODSTATION.get())) {
                    network.getSources().add(checkPos.immutable());
                }
            }
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.APC.get())) {
                    network.getConsumers().add(checkPos.immutable());
                }
            }
        }
    }
}
