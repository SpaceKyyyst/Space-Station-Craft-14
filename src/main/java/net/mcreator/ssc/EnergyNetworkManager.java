
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
        
        if (triggerState.getBlock() instanceof SheathingBlock) {
            for (CableType type : CableType.values()) {
                rebuildNetworksForType(level, pos, type);
            }
            return;
        }

        for (CableType type : CableType.values()) {
            List<EnergyNetwork> networks = getNetworks(level, type);
            
            // Зачищаем уничтоженную точку из всех списков
            for (EnergyNetwork net : networks) {
                net.getCables().remove(pos);
                net.getSources().remove(pos);
                net.getConsumers().remove(pos);
            }
            networks.removeIf(net -> net.getCables().isEmpty());

            // Перестраиваем смежные ветки от четырёх соседей
            for (Direction dir : HORIZONTAL_DIRECTIONS) {
                BlockPos neighborPos = pos.relative(dir);
                if (level.getBlockState(neighborPos).getBlock() instanceof SheathingBlock) {
                    rebuildNetworksForType(level, neighborPos, type);
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
