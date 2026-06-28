
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

    // УМНЫЙ МЕТОД ОБНОВЛЕНИЯ: Находит кабель под прибором, на какой бы высоте он ни висел!
    public static void updatePosition(LevelAccessor level, BlockPos pos) {
        if (level == null || level.isClientSide()) return;

        System.out.println("=== [SSC14-NETWORK] Сигнал обновления от точки: " + pos + " ===");

        BlockState triggerState = level.getBlockState(pos);
        
        // Если точку обновил сам кабель, просто перестраиваем от неё
        if (triggerState.getBlock() instanceof SheathingBlock) {
            for (CableType type : CableType.values()) {
                rebuildNetworksForType(level, pos, type);
            }
            return;
        }

        // Если точку обновил прибор (например, ЛКП или Подстанция), ищем кабель строго СНИЗУ от него!
        // Сканируем до 5 блоков вниз, так как ЛКП может подключаться на высоте до 5 блоков над кабелем.
        BlockPos cableTargetPos = null;
        for (int yDown = 0; yDown <= 5; yDown++) {
            BlockPos checkPos = pos.below(yDown);
            if (level.getBlockState(checkPos).getBlock() instanceof SheathingBlock) {
                cableTargetPos = checkPos;
                break; // Нашли кабель!
            }
        }

        // Если нашли кабель под прибором — запускаем пересчёт сетей строго из точки КАБЕЛЯ
        if (cableTargetPos != null) {
            System.out.println("[SSC14-NETWORK] Найден кабель под прибором в точке: " + cableTargetPos);
            for (CableType type : CableType.values()) {
                rebuildNetworksForType(level, cableTargetPos, type);
            }
        } else {
            // На случай, если прибор ставится как-то иначе, оставляем старое базовое обновление
            for (CableType type : CableType.values()) {
                rebuildNetworksForType(level, pos, type);
                rebuildNetworksForType(level, pos.below(), type);
            }
        }
    }

    private static void rebuildNetworksForType(LevelAccessor level, BlockPos startPos, CableType type) {
        List<EnergyNetwork> networks = getNetworks(level, type);
        
        for (EnergyNetwork net : networks) {
            net.getCables().remove(startPos);
            net.getSources().remove(startPos);
            net.getConsumers().remove(startPos);
        }
        networks.removeIf(net -> net.getCables().isEmpty());

        BlockState currentState = level.getBlockState(startPos);
        
        if (!(currentState.getBlock() instanceof SheathingBlock) || !type.hasCable(currentState)) {
            for (Direction dir : HORIZONTAL_DIRECTIONS) {
                BlockPos neighbor = startPos.relative(dir);
                if (level.getBlockState(neighbor).getBlock() instanceof SheathingBlock) {
                    rebuildNeighbourBranch(level, neighbor, type);
                }
            }
            return;
        }

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos.immutable());
        visited.add(startPos.immutable());

        for (Direction dir : HORIZONTAL_DIRECTIONS) {
            BlockPos neighbor = startPos.relative(dir);
            if (type.hasCable(level.getBlockState(neighbor)) && !visited.contains(neighbor)) {
                queue.add(neighbor.immutable());
                visited.add(neighbor.immutable());
            }
        }

        EnergyNetwork newNetwork = new EnergyNetwork(type);
        networks.add(newNetwork);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            mapCableToNetwork(level, newNetwork, current);

            for (Direction dir : HORIZONTAL_DIRECTIONS) {
                BlockPos next = current.relative(dir);
                if (!visited.contains(next) && type.hasCable(level.getBlockState(next))) {
                    queue.add(next.immutable());
                    visited.add(next.immutable());
                }
            }
        }
        System.out.println("[SSC14-NETWORK] Сеть обновлена [" + type + "]. Кабелей в цепи: " + newNetwork.getCableCount());
    }

    private static void rebuildNeighbourBranch(LevelAccessor level, BlockPos pos, CableType type) {
        List<EnergyNetwork> networks = getNetworks(level, type);
        if (!type.hasCable(level.getBlockState(pos))) return;
        if (networks.stream().anyMatch(net -> net.getCables().contains(pos))) return;

        EnergyNetwork branchNet = new EnergyNetwork(type);
        networks.add(branchNet);
        
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        queue.add(pos.immutable());
        visited.add(pos.immutable());

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            mapCableToNetwork(level, branchNet, current);
            for (Direction dir : HORIZONTAL_DIRECTIONS) {
                BlockPos next = current.relative(dir);
                if (!visited.contains(next) && type.hasCable(level.getBlockState(next))) {
                    queue.add(next.immutable());
                    visited.add(next.immutable());
                }
            }
        }
    }

    private static void mapCableToNetwork(LevelAccessor level, EnergyNetwork network, BlockPos cablePos) {
        network.addCable(cablePos);

        // 1. СТРОГАЯ ГЕОМЕТРИЯ ДЛЯ НИЗКОВОЛЬТНОЙ СЕТИ (LV)
        if (network.getType() == CableType.LV) {
            // Источник: ЛКП (APC) — строго вертикально вверх от 1 до 5 блоков
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.APC.get())) {
                    network.getSources().add(checkPos.immutable());
                    break; 
                }
            }

            // Потребители: Приборы (Лампы) — куб 5x5 по горизонтали, строго НАД кабелем (высота 1-6)
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
        
        // 2. СТРОГАЯ ГЕОМЕТРИЯ ДЛЯ ВЫСОКОВОЛЬТНОЙ СЕТИ (HV)
        else if (network.getType() == CableType.HV) {
            // Проверяем только строго вверх на 1 и 2 блока
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
        
        // 3. СТРОГАЯ ГЕОМЕТРИЯ ДЛЯ СРЕДНЕВОЛЬТНОЙ СЕТИ (MV)
        else if (network.getType() == CableType.MV) {
            // Подстанция (Источник) — строго вверх на 1 и 2 блока
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.PODSTATION.get())) {
                    network.getSources().add(checkPos.immutable());
                }
            }
            
            // ЛКП / APC (Потребитель) — строго вверх от 1 до 5 блоков
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (level.getBlockState(checkPos).is(Ssc14ModBlocks.APC.get())) {
                    network.getConsumers().add(checkPos.immutable());
                }
            }
        }
    }
}
