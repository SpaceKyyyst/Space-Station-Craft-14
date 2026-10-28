package net.mcreator.ssc.energy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.ssc14.content.block.energy.SheathingBlock;
import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Server-thread-only topology state. A batch rebuilds only components touching changed positions. */
public final class EnergyTopologyService {
    public static final int MAX_WORK_UNITS_PER_TICK = 128;
    private static final long MAX_REBUILD_NANOS_PER_TICK = 2_000_000L;
    private static final int DEVICE_RADIUS = 2;
    private static final int DEVICE_HEIGHT = 6;
    private static final Direction[] HORIZONTAL = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    private static final Map<LevelAccessor, WorldTopology> WORLDS = new WeakHashMap<>();

    private static final class Layer {
        final List<EnergyNetwork> networks = new ArrayList<>();
        final Map<EnergyNetwork, Integer> networkIndices = new HashMap<>();

        void publish(EnergyNetwork network) {
            if (networkIndices.containsKey(network)) return;
            networkIndices.put(network, networks.size());
            networks.add(network);
        }

        void retire(EnergyNetwork network) {
            var index = networkIndices.remove(network);
            if (index == null) return;
            var last = networks.remove(networks.size() - 1);
            if (index < networks.size()) {
                networks.set(index, last);
                networkIndices.put(last, index);
            }
        }
        final Map<BlockPos, EnergyNetwork> owners = new HashMap<>();
        final Map<Long, Map<EnergyNetwork, Integer>> networksByChunk = new HashMap<>();
        RebuildJob job;

        void addOwner(BlockPos cable, EnergyNetwork network) {
            if (owners.get(cable) == network) return;
            removeOwner(cable);
            owners.put(cable, network);
            networksByChunk.computeIfAbsent(chunkKey(cable.getX() >> 4, cable.getZ() >> 4), ignored -> new HashMap<>())
                    .merge(network, 1, Integer::sum);
        }

        void removeOwner(BlockPos cable) {
            var previous = owners.remove(cable);
            if (previous == null) return;
            long key = chunkKey(cable.getX() >> 4, cable.getZ() >> 4);
            var bucket = networksByChunk.get(key);
            if (bucket != null) {
                bucket.computeIfPresent(previous, (network, count) -> count == 1 ? null : count - 1);
                if (bucket.isEmpty()) networksByChunk.remove(key);
            }
        }
    }

    private static final class WorldTopology {
        final Map<CableType, Layer> layers = new EnumMap<>(CableType.class);
        long budgetTick = Long.MIN_VALUE;
        int workUnits;
        long elapsedNanos;
        int nextLayer;
        Layer layer(CableType type) { return layers.computeIfAbsent(type, ignored -> new Layer()); }
    }

    private EnergyTopologyService() { }

    private static WorldTopology world(LevelAccessor level) {
        return WORLDS.computeIfAbsent(level, ignored -> new WorldTopology());
    }

    public static EnergyNetwork networkAt(LevelAccessor level, CableType type, BlockPos cable) {
        var state = WORLDS.get(level);
        if (state == null) return null;
        var network = state.layer(type).owners.get(cable);
        return network != null && network.isTopologyReady() ? network : null;
    }

    public static List<EnergyNetwork> networks(LevelAccessor level, CableType type) {
        return Collections.unmodifiableList(world(level).layer(type).networks);
    }

    public static void invalidate(LevelAccessor level, BlockPos pos) {
        if (level == null || level.isClientSide()) return;
        var state = world(level);
        boolean cable = isLoaded(level, pos) && level.getBlockState(pos).getBlock() instanceof SheathingBlock;
        for (var type : CableType.values()) {
            var layer = state.layer(type);
            boolean cableChange = cable || layer.owners.containsKey(pos);
            if (cableChange) {
                request(layer, type, pos);
                for (var direction : HORIZONTAL) request(layer, type, pos.relative(direction));
            } else {
                for (int y = 0; y <= DEVICE_HEIGHT; y++) for (int x = -DEVICE_RADIUS; x <= DEVICE_RADIUS; x++) {
                    for (int z = -DEVICE_RADIUS; z <= DEVICE_RADIUS; z++) request(layer, type, pos.offset(x, -y, z));
                }
            }
        }
    }

    private static void request(Layer layer, CableType type, BlockPos position) {
        if (layer.job == null) layer.job = new RebuildJob(layer, type);
        layer.job.request(position);
    }

    private static long chunkKey(int x, int z) {
        return ((long) x << 32) | (z & 0xffffffffL);
    }

    public static void chunkAvailabilityChanged(LevelAccessor level, ChunkPos chunk) {
        var world = WORLDS.get(level);
        if (world == null) return;
        for (var type : CableType.values()) {
            var layer = world.layer(type);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    var networks = layer.networksByChunk.get(chunkKey(chunk.x() + dx, chunk.z() + dz));
                    if (networks == null) continue;
                    if (layer.job == null) layer.job = new RebuildJob(layer, type);
                    for (var network : networks.keySet()) layer.job.addSource(network);
                    layer.job.generation.ready = false;
                    layer.job.restart = true;
                }
            }
        }
    }

    public static void unloadWorld(LevelAccessor level) { WORLDS.remove(level); }

    public static void refreshPending(LevelAccessor level) {
        var state = WORLDS.get(level);
        if (state == null) return;
        long tick = level instanceof Level world ? world.getGameTime() : 0;
        if (state.budgetTick != tick) {
            state.budgetTick = tick;
            state.workUnits = 0;
            state.elapsedNanos = 0;
        }
        if (state.workUnits >= MAX_WORK_UNITS_PER_TICK || state.elapsedNanos >= MAX_REBUILD_NANOS_PER_TICK) return;
        long start = System.nanoTime();
        try (var timing = net.ssc14.core.diagnostics.TickPhase.start("energy_topology", state.workUnits)) {
            int idleLayers = 0;
            var types = CableType.values();
            while (state.workUnits < MAX_WORK_UNITS_PER_TICK
                    && state.elapsedNanos + System.nanoTime() - start < MAX_REBUILD_NANOS_PER_TICK) {
                var layer = state.layer(types[state.nextLayer]);
                state.nextLayer = (state.nextLayer + 1) % types.length;
                if (layer.job == null) {
                    if (++idleLayers == types.length) break;
                    continue;
                }
                idleLayers = 0;
                if (layer.job.step(level)) layer.job = null;
                state.workUnits++;
            }
        } finally {
            state.elapsedNanos += System.nanoTime() - start;
        }
    }

    public static int workUnitsThisTick(LevelAccessor level) {
        var state = WORLDS.get(level);
        return state == null ? 0 : state.workUnits;
    }

    public static boolean rebuilding(LevelAccessor level) {
        var state = WORLDS.get(level);
        return state != null && state.layers.values().stream().anyMatch(layer -> layer.job != null);
    }

    private static final class Generation {
        boolean ready;
    }

    /** Each step advances one seed, cable, publication or retirement; no whole-network copy. */
    private static final class RebuildJob {
        final Layer layer;
        final CableType type;
        final Set<BlockPos> requested = new LinkedHashSet<>();
        final List<EnergyNetwork> sources = new ArrayList<>();
        final Set<EnergyNetwork> knownSources = new HashSet<>();
        final ArrayDeque<Iterator<EnergyNetwork>> abandoned = new ArrayDeque<>();
        List<EnergyNetwork> drafts = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> frontier = new ArrayDeque<>();
        Iterator<BlockPos> requestedIterator = Collections.emptyIterator();
        Iterator<BlockPos> sourceIterator = Collections.emptyIterator();
        Generation generation = new Generation();
        EnergyNetwork current;
        boolean restart = true;
        int sourceIndex;
        int publishIndex;
        int retireIndex;
        int phase;

        RebuildJob(Layer layer, CableType type) { this.layer = layer; this.type = type; }

        void addSource(EnergyNetwork network) {
            if (network != null && knownSources.add(network)) {
                network.invalidateTopology();
                sources.add(network);
            }
        }

        void request(BlockPos position) {
            generation.ready = false;
            addSource(layer.owners.get(position));
            requested.add(position.immutable());
            restart = true;
        }

        boolean step(LevelAccessor level) {
            if (restart) {
                abandoned.add(drafts.iterator());
                drafts = new ArrayList<>();
                frontier = new ArrayDeque<>();
                visited = new HashSet<>();
                requestedIterator = requested.iterator();
                sourceIterator = Collections.emptyIterator();
                generation = new Generation();
                sourceIndex = publishIndex = retireIndex = phase = 0;
                current = null;
                restart = false;
                return false;
            }
            if (!abandoned.isEmpty()) {
                var iterator = abandoned.peek();
                if (iterator.hasNext()) {
                    var previous = iterator.next();
                    addSource(previous);
                    layer.retire(previous);
                } else abandoned.remove();
                return false;
            }
            if (phase == 1) {
                if (publishIndex < drafts.size()) layer.publish(drafts.get(publishIndex++));
                else phase = 2;
                return false;
            }
            if (phase == 2) {
                if (retireIndex < sources.size()) {
                    var retired = sources.get(retireIndex);
                    if (!layer.networkIndices.containsKey(retired) || retired.isShutdownProcessed()) {
                        layer.retire(retired);
                        retireIndex++;
                    }
                }
                else {
                    generation.ready = true;
                    return true;
                }
                return false;
            }
            if (!frontier.isEmpty()) {
                var position = frontier.remove();
                if (!isCable(level, position, type)) {
                    layer.removeOwner(position);
                    return false;
                }
                var owner = layer.owners.get(position);
                if (owner != current) addSource(owner);
                CableDeviceMapper.attach(level, current, position);
                layer.addOwner(position, current);
                for (var direction : HORIZONTAL) {
                    var neighbor = position.relative(direction);
                    if (!visited.contains(neighbor) && isCable(level, neighbor, type)) {
                        visited.add(neighbor);
                        frontier.add(neighbor);
                    }
                }
                return false;
            }
            current = null;
            BlockPos seed;
            if (requestedIterator.hasNext()) seed = requestedIterator.next();
            else if (sourceIterator.hasNext()) seed = sourceIterator.next();
            else if (sourceIndex < sources.size()) {
                sourceIterator = sources.get(sourceIndex++).getCables().iterator();
                return false;
            } else {
                phase = 1;
                return false;
            }
            if (!visited.add(seed)) return false;
            if (!isCable(level, seed, type)) {
                layer.removeOwner(seed);
                return false;
            }
            var token = generation;
            current = new EnergyNetwork(type, () -> token.ready);
            drafts.add(current);
            frontier.add(seed);
            return false;
        }
    }

    private static boolean isLoaded(LevelAccessor level, BlockPos pos) {
        return !(level instanceof Level world) || world.isLoaded(pos);
    }

    private static boolean isCable(LevelAccessor level, BlockPos pos, CableType type) {
        if (!isLoaded(level, pos)) return false;
        var state = level.getBlockState(pos);
        return state.getBlock() instanceof SheathingBlock && type.hasCable(state);
    }
}
