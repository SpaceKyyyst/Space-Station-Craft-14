package net.mcreator.ssc;

import net.mcreator.ssc.block.LampBlock;
import net.mcreator.ssc.block.SheathingBlock;
import net.mcreator.ssc.energy.ConnectedComponents;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/** Server-thread topology cache. Only loaded chunks participate in electrical networks. */
public class EnergyNetworkManager {
    private static final Map<LevelAccessor, Map<CableType, List<EnergyNetwork>>> WORLD_NETWORKS = new WeakHashMap<>();
    private static final Map<LevelAccessor, Set<BlockPos>> PENDING_UPDATES = new WeakHashMap<>();
    private static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    private static final int LAMP_HORIZONTAL_RADIUS = 2;
    private static final int LAMP_HEIGHT = 6;
    private static final int APC_HEIGHT = 5;
    private static final int SUBSTATION_HEIGHT = 2;

    public static List<EnergyNetwork> getNetworks(LevelAccessor level, CableType type) {
        return WORLD_NETWORKS.computeIfAbsent(level, key -> new EnumMap<>(CableType.class))
                .computeIfAbsent(type, key -> new ArrayList<>());
    }

    public static void updatePosition(LevelAccessor level, BlockPos pos) {
        if (level == null || level.isClientSide()) {
            return;
        }
        rebuildNetworks(level, Set.of(pos.immutable()));
    }

    /** Lifecycle hooks only queue work; chunk loading must finish before the graph reads the level. */
    public static void queueUpdate(LevelAccessor level, BlockPos pos) {
        if (level != null && !level.isClientSide()) {
            PENDING_UPDATES.computeIfAbsent(level, key -> new LinkedHashSet<>()).add(pos.immutable());
        }
    }

    public static void applyPendingUpdates(LevelAccessor level) {
        Set<BlockPos> changedPositions = PENDING_UPDATES.remove(level);
        if (changedPositions != null && !changedPositions.isEmpty()) {
            rebuildNetworks(level, changedPositions);
        }
    }

    private static void rebuildNetworks(LevelAccessor level, Set<BlockPos> changedPositions) {
        Set<BlockPos> candidates = new LinkedHashSet<>();
        for (BlockPos pos : changedPositions) {
            candidates.add(pos);
            candidates.addAll(horizontalNeighbors(pos));
            // Also works after device removal, when its previous block type is unavailable.
            for (int y = 1; y <= LAMP_HEIGHT; y++) {
                for (int x = -LAMP_HORIZONTAL_RADIUS; x <= LAMP_HORIZONTAL_RADIUS; x++) {
                    for (int z = -LAMP_HORIZONTAL_RADIUS; z <= LAMP_HORIZONTAL_RADIUS; z++) {
                        candidates.add(pos.offset(x, -y, z));
                    }
                }
            }
        }
        Set<BlockPos> previousLamps = new HashSet<>();
        for (BlockPos pos : changedPositions) {
            if (isLoaded(level, pos) && level.getBlockState(pos).is(Ssc14ModBlocks.LAMP.get())) {
                previousLamps.add(pos);
            }
        }
        for (EnergyNetwork network : getNetworks(level, CableType.LV)) {
            previousLamps.addAll(network.getConsumers());
        }
        for (CableType type : CableType.values()) {
            rebuildNetworksForType(level, candidates, type);
        }
        for (EnergyNetwork network : getNetworks(level, CableType.LV)) {
            previousLamps.removeAll(network.getConsumers());
        }
        for (BlockPos lamp : previousLamps) {
            if (isLoaded(level, lamp)) {
                BlockState state = level.getBlockState(lamp);
                if (state.is(Ssc14ModBlocks.LAMP.get()) && state.getValue(LampBlock.ACTIVE)) {
                    level.setBlock(lamp, state.setValue(LampBlock.ACTIVE, false), 3);
                }
            }
        }
    }

    private static void rebuildNetworksForType(LevelAccessor level, Set<BlockPos> candidates, CableType type) {
        List<EnergyNetwork> networks = getNetworks(level, type);
        Set<BlockPos> seeds = new LinkedHashSet<>(candidates);
        // Keeping every old cable as a seed preserves both sides of a removed bridge.
        for (EnergyNetwork network : networks) {
            seeds.addAll(network.getCables());
        }
        List<EnergyNetwork> rebuilt = new ArrayList<>();
        for (Set<BlockPos> component : ConnectedComponents.find(seeds,
                pos -> hasCable(level, pos, type), EnergyNetworkManager::horizontalNeighbors)) {
            EnergyNetwork network = new EnergyNetwork(type);
            for (BlockPos cable : component) {
                mapCableToNetwork(level, network, cable);
            }
            rebuilt.add(network);
        }
        networks.clear();
        networks.addAll(rebuilt);
    }

    private static List<BlockPos> horizontalNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>(HORIZONTAL_DIRECTIONS.length);
        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            neighbors.add(pos.relative(direction));
        }
        return neighbors;
    }

    private static boolean hasCable(LevelAccessor level, BlockPos pos, CableType type) {
        if (!isLoaded(level, pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof SheathingBlock && type.hasCable(state);
    }

    private static boolean isLoaded(LevelAccessor level, BlockPos pos) {
        return level.hasChunk(pos.getX() >> 4, pos.getZ() >> 4);
    }

    private static void mapCableToNetwork(LevelAccessor level, EnergyNetwork network, BlockPos cablePos) {
        network.addCable(cablePos);
        switch (network.getType()) {
            case LV -> {
                for (int y = 1; y <= APC_HEIGHT; y++) {
                    BlockPos pos = cablePos.above(y);
                    if (isLoaded(level, pos) && level.getBlockState(pos).is(Ssc14ModBlocks.APC.get())) {
                        network.getSources().add(pos);
                        break;
                    }
                }
                for (int y = 1; y <= LAMP_HEIGHT; y++) {
                    for (int x = -LAMP_HORIZONTAL_RADIUS; x <= LAMP_HORIZONTAL_RADIUS; x++) {
                        for (int z = -LAMP_HORIZONTAL_RADIUS; z <= LAMP_HORIZONTAL_RADIUS; z++) {
                            BlockPos pos = cablePos.offset(x, y, z);
                            if (isLoaded(level, pos) && level.getBlockState(pos).is(Ssc14ModBlocks.LAMP.get())) {
                                network.getConsumers().add(pos);
                            }
                        }
                    }
                }
            }
            case MV -> {
                mapVerticalDevices(level, network, cablePos, true);
                for (int y = 1; y <= APC_HEIGHT; y++) {
                    BlockPos pos = cablePos.above(y);
                    if (isLoaded(level, pos) && level.getBlockState(pos).is(Ssc14ModBlocks.APC.get())) {
                        network.getConsumers().add(pos);
                    }
                }
            }
            case HV -> mapVerticalDevices(level, network, cablePos, false);
        }
    }

    private static void mapVerticalDevices(LevelAccessor level, EnergyNetwork network, BlockPos cablePos,
            boolean mediumVoltage) {
        for (int y = 1; y <= SUBSTATION_HEIGHT; y++) {
            BlockPos pos = cablePos.above(y);
            if (!isLoaded(level, pos)) {
                continue;
            }
            BlockState state = level.getBlockState(pos);
            if (state.is(Ssc14ModBlocks.PODSTATION.get())) {
                (mediumVoltage ? network.getSources() : network.getConsumers()).add(pos);
            } else if (!mediumVoltage && state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                network.getSources().add(pos);
            }
        }
    }
}
