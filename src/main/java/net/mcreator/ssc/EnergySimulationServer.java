package net.mcreator.ssc;

import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;
import net.ssc14.content.energy.EnergyNetworkService;

import net.mcreator.ssc.block.LampBlock;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.ssc14.content.block.energy.EnergyStorage;
import net.ssc14.content.block.energy.PodstationBlockEntity;
import net.ssc14.core.energy.EnergyAllocation;
import net.ssc14.core.energy.EnergyTickBudget;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber
public class EnergySimulationServer {
    private static final long TICKS_PER_SECOND = 20;
    private static final long GENERATOR_WATT = 100000;
    private static final long SUBSTATION_WATT = 150000;
    private static final long APC_WATT = 15000;
    private static final long MAX_DEVICE_INPUT_JOULES = 50000 / TICKS_PER_SECOND;
    // Lamps and airlocks each consume 20 W: one joule per server tick.
    private static final long LV_LOAD_WATT = 20;
    private static final long LV_LOAD_JOULES_PER_TICK = 1;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        try (var timing = net.ssc14.core.diagnostics.TickPhase.start("energy_simulation", 0)) {
        for (ServerLevel level : event.getServer().getAllLevels()) {
            EnergyNetworkService.applyPendingUpdates(level);
            TickContext tick = prepareTick(level);
            simulateNetworkLayer(level, CableType.HV, tick);
            simulateNetworkLayer(level, CableType.MV, tick);
            simulateNetworkLayer(level, CableType.LV, tick);
            tick.loads.apply();
            EnergyNetworkTelemetry.publishToolData(level);
        }
        }
    }

    /** A fresh context per dimension prevents equal coordinates from sharing energy snapshots. */
    private static TickContext prepareTick(ServerLevel level) {
        TickContext tick = new TickContext(level);
        for (CableType type : CableType.values()) {
            for (EnergyNetwork network : EnergyNetworkService.networks(level, type)) {
                if (!network.isTopologyReady()) continue;
                network.getSources().removeIf(pos -> !isSource(level, pos, type));
                network.getConsumers().removeIf(pos -> type == CableType.LV ? !tick.loads.track(pos) : !isConsumer(level, pos, type));
                for (BlockPos pos : network.getSources()) {
                    captureDevice(level, pos, tick);
                }
                if (type != CableType.LV) for (BlockPos pos : network.getConsumers()) {
                    captureDevice(level, pos, tick);
                }
            }
        }
        Map<BlockPos, Set<BlockPos>> apcLoads = new HashMap<>();
        for (EnergyNetwork network : EnergyNetworkService.networks(level, CableType.LV)) {
            if (!network.isTopologyReady()) continue;
            for (BlockPos source : network.getSources()) {
                Set<BlockPos> lamps = apcLoads.computeIfAbsent(source, key -> new HashSet<>());
                for (BlockPos lamp : network.getConsumers()) {
                    if (tick.loads.needsPower(lamp)) {
                        lamps.add(lamp);
                    }
                }
            }
        }
        for (Map.Entry<BlockPos, EnergyStorage> entry : tick.storage.entrySet()) {
            EnergyStorage storage = entry.getValue();
            storage.setNetworkCurrentPower(0);
            storage.setNetworkTheoreticalSupply(0);
            storage.setNetworkIdealConsumption(0);
            if (storage instanceof ApcBlockEntity apc) {
                apc.lastTickReceivingJoules = 0;
                apc.lastTickDischargingJoules = 0;
                apc.setNetworkIdealConsumption(apcLoads.getOrDefault(entry.getKey(), Set.of()).size() * LV_LOAD_WATT);
                apc.serverTick();
            }
        }
        return tick;
    }

    private static boolean isSource(ServerLevel level, BlockPos pos, CableType type) {
        if (!level.isLoaded(pos)) {
            return false;
        }
        return switch (type) {
            case HV -> level.getBlockState(pos).is(Ssc14ModBlocks.DEBU_GGENERATOR.get());
            case MV -> level.getBlockEntity(pos) instanceof PodstationBlockEntity;
            case LV -> level.getBlockEntity(pos) instanceof ApcBlockEntity;
        };
    }

    private static boolean isConsumer(ServerLevel level, BlockPos pos, CableType type) {
        if (!level.isLoaded(pos)) {
            return false;
        }
        return switch (type) {
            case HV -> level.getBlockEntity(pos) instanceof PodstationBlockEntity;
            case MV -> level.getBlockEntity(pos) instanceof ApcBlockEntity;
            case LV -> level.getBlockState(pos).is(Ssc14ModBlocks.LAMP.get());
        };
    }

    private static void captureDevice(ServerLevel level, BlockPos pos, TickContext tick) {
        if (tick.budgets.containsKey(pos)) {
            return;
        }
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof EnergyStorage storage) {
            long output = entity instanceof ApcBlockEntity ? APC_WATT : SUBSTATION_WATT;
            tick.storage.put(pos, storage);
            tick.budgets.put(pos, new EnergyTickBudget(storage.getStoredEnergy(), storage.getMaxEnergy(),
                    output / TICKS_PER_SECOND, MAX_DEVICE_INPUT_JOULES));
        } else if (level.getBlockState(pos).is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
            long output = GENERATOR_WATT / TICKS_PER_SECOND;
            tick.budgets.put(pos, new EnergyTickBudget(output, output, output, 0));
        }
    }

    private static void simulateNetworkLayer(ServerLevel level, CableType type, TickContext tick) {
        for (EnergyNetwork network : EnergyNetworkService.networks(level, type)) {
            network.resetTickStats();
            if (!network.isTopologyReady()) {
                if (type == CableType.LV) for (var lamp : network.getConsumers()) tick.loads.track(lamp);
                network.acknowledgeShutdown();
                continue;
            }
            Map<BlockPos, Long> sources = availableSources(network, tick);
            long available = sum(sources.values());
            long delivered;
            if (type == CableType.LV) {
                delivered = supplyLoads(level, network, available, tick);
            } else {
                delivered = supplyStorage(network, available, tick);
            }
            withdrawDeliveredEnergy(network, sources, delivered, tick);
            network.currentPower = delivered * TICKS_PER_SECOND;
            captureNetworkStorage(network, tick);
            EnergyNetworkTelemetry.publishCableData(level, network);
        }
    }

    private static Map<BlockPos, Long> availableSources(EnergyNetwork network, TickContext tick) {
        Map<BlockPos, Long> sources = new LinkedHashMap<>();
        for (BlockPos pos : network.getSources()) {
            EnergyStorage storage = tick.storage.get(pos);
            if (storage instanceof ApcBlockEntity apc && !apc.isMainBreakerEnabled()) {
                continue;
            }
            long rating = switch (network.getType()) {
                case HV -> GENERATOR_WATT;
                case MV -> SUBSTATION_WATT;
                case LV -> APC_WATT;
            };
            network.theoreticalSupply += rating;
            long available = tick.budgets.get(pos).availableOutput();
            if (storage != null) {
                available = Math.min(available, Math.max(0, storage.getStoredEnergy()));
                storage.setNetworkTheoreticalSupply(rating);
            }
            sources.put(pos, available);
        }
        return sources;
    }

    private static long supplyLoads(ServerLevel level, EnergyNetwork network, long available, TickContext tick) {
        long count = 0;
        for (BlockPos pos : network.getConsumers()) {
            if (tick.loads.needsPower(pos)) count++;
        }
        long demand = count * LV_LOAD_JOULES_PER_TICK;
        network.idealConsumption = count * LV_LOAD_WATT;
        if (available < demand) return 0;
        for (BlockPos pos : network.getConsumers()) tick.loads.power(pos);
        return demand;
    }

    private static long supplyStorage(EnergyNetwork network, long available, TickContext tick) {
        List<BlockPos> consumers = new ArrayList<>(network.getConsumers());
        long[] demands = new long[consumers.size()];
        for (int i = 0; i < consumers.size(); i++) {
            BlockPos pos = consumers.get(i);
            EnergyStorage storage = tick.storage.get(pos);
            demands[i] = Math.min(tick.budgets.get(pos).remainingInput(),
                    Math.max(0, storage.getMaxEnergy() - storage.getStoredEnergy()));
            network.idealConsumption += demands[i] * TICKS_PER_SECOND;
        }
        long[] allocation = EnergyAllocation.distribute(available, demands);
        long delivered = 0;
        for (int i = 0; i < consumers.size(); i++) {
            BlockPos pos = consumers.get(i);
            long accepted = tick.budgets.get(pos).acceptInput(allocation[i]);
            if (accepted > 0) {
                EnergyStorage storage = tick.storage.get(pos);
                storage.setStoredEnergySimulation(storage.getStoredEnergy() + accepted);
                if (storage instanceof ApcBlockEntity apc) {
                    apc.lastTickReceivingJoules += accepted;
                }
                delivered += accepted;
            }
        }
        return delivered;
    }

    private static void withdrawDeliveredEnergy(EnergyNetwork network, Map<BlockPos, Long> sources,
            long delivered, TickContext tick) {
        if (delivered <= 0) return;
        long[] allocations = EnergyAllocation.distribute(delivered,
                sources.values().stream().mapToLong(Long::longValue).toArray());
        int index = 0;
        for (BlockPos pos : sources.keySet()) {
            long withdrawn = tick.budgets.get(pos).takeOutput(allocations[index++]);
            EnergyStorage storage = tick.storage.get(pos);
            if (storage != null && withdrawn > 0) {
                storage.setStoredEnergySimulation(storage.getStoredEnergy() - withdrawn);
                storage.setNetworkCurrentPower(storage.getNetworkCurrentPower() + withdrawn * TICKS_PER_SECOND);
                network.batteryPower += withdrawn * TICKS_PER_SECOND;
                if (storage instanceof ApcBlockEntity apc) {
                    apc.lastTickDischargingJoules += withdrawn;
                }
            }
        }
    }

    private static void captureNetworkStorage(EnergyNetwork network, TickContext tick) {
        for (BlockPos pos : network.getSources()) {
            EnergyStorage storage = tick.storage.get(pos);
            if (storage != null) {
                network.inputStored += storage.getStoredEnergy();
                network.inputMax += storage.getMaxEnergy();
            }
        }
        for (BlockPos pos : network.getConsumers()) {
            EnergyStorage storage = tick.storage.get(pos);
            if (storage != null) {
                network.outputStored += storage.getStoredEnergy();
                network.outputMax += storage.getMaxEnergy();
            }
        }
        if (network.getType() == CableType.LV) {
            network.outputStored = network.inputStored;
            network.outputMax = network.inputMax;
        }
    }

    private static long sum(Iterable<Long> values) {
        long total = 0;
        for (long value : values) {
            total = Math.addExact(total, value);
        }
        return total;
    }

    private static final class TickContext {
        private final Map<BlockPos, EnergyTickBudget> budgets = new HashMap<>();
        private final Map<BlockPos, EnergyStorage> storage = new HashMap<>();
        private final net.ssc14.content.energy.LowVoltageTickBatch loads;
        TickContext(ServerLevel level) { loads = new net.ssc14.content.energy.LowVoltageTickBatch(level); }
    }
}
