package net.mcreator.ssc;

import net.mcreator.ssc.block.entity.PodstationBlockEntity;
import net.mcreator.ssc.block.entity.APCBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber
public class EnergySimulationServer {

    private static final long DEBUG_GEN_WATT = 100000;
    private static final long DEBUG_GEN_JOULES_PER_TICK = DEBUG_GEN_WATT / 20;
    private static final long MAX_DEVICE_INPUT_JOULES = 50000 / 20;
    private static final int TICKS_PER_SECOND = 20;
    private static final long SUBSTATION_OUTPUT_WATT = 150000;
    private static final long APC_OUTPUT_WATT = 15000;
    private static final long LAMP_WATT = 20;
    private static final long LAMP_JOULES_PER_TICK = 1;

    private record SourceKey(ServerLevel level, BlockPos position) { }
    private static final Map<SourceKey, Long> energySnapshot = new HashMap<>();
    private static int diagnosticLogCooldown = 0;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        try (var timing = net.mcreator.ssc.diagnostics.TickPhase.start("energy_simulation", 0)) {
            simulate(event);
        }
    }

    private static void simulate(ServerTickEvent.Post event) {
        energySnapshot.clear();

        boolean displayDiag = false;
        if (diagnosticLogCooldown++ >= 20) {
            displayDiag = true;
            diagnosticLogCooldown = 0;
        }

        for (ServerLevel serverLevel : event.getServer().getAllLevels()) {
            EnergyNetworkManager.refreshPending(serverLevel);
            for (CableType type : CableType.values()) {
                List<EnergyNetwork> networks = EnergyNetworkManager.getNetworks(serverLevel, type);

                if (displayDiag && !networks.isEmpty()) {
                    Ssc14Mod.LOGGER.debug("[SSC14-SIM-DIAG] Активных цепей [" + type + "]: " + networks.size());
                }

                for (EnergyNetwork network : networks) {
                    if (!network.isTopologyReady()) continue;
                    Iterator<BlockPos> sourceIterator = network.getSources().iterator();

                    while (sourceIterator.hasNext()) {
                        BlockPos pos = sourceIterator.next();
                        if (!serverLevel.isLoaded(pos)) continue;

                        BlockEntity be = serverLevel.getBlockEntity(pos);
                        BlockState state = serverLevel.getBlockState(pos);

                        if (type == CableType.HV && !state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                            sourceIterator.remove();
                        } else if (type == CableType.MV && !(be instanceof PodstationBlockEntity)) {
                            sourceIterator.remove();
                        } else if (type == CableType.LV && !(be instanceof APCBlockEntity)) {
                            sourceIterator.remove();
                        } else if (!energySnapshot.containsKey(new SourceKey(serverLevel, pos))) {
                            if (be instanceof PodstationBlockEntity podstation) {
                                energySnapshot.put(new SourceKey(serverLevel, pos), podstation.getStoredEnergy());
                            } else if (be instanceof APCBlockEntity apc) {
                                energySnapshot.put(new SourceKey(serverLevel, pos), apc.getStoredEnergy());
                                apc.lastTickDischargingJoules = 0;
                                apc.lastTickReceivingJoules = 0;
                                apc.serverTick();
                            }
                        }
                    }
                }
            }
        }

        for (ServerLevel serverLevel : event.getServer().getAllLevels()) {
            simulateNetworkLayer(serverLevel, CableType.HV);
            simulateNetworkLayer(serverLevel, CableType.MV);
            simulateNetworkLayer(serverLevel, CableType.LV);
        }

        energySnapshot.clear();
    }
    private static void simulateNetworkLayer(ServerLevel level, CableType type) {
        List<EnergyNetwork> networks = EnergyNetworkManager.getNetworks(level, type);
        if (networks.isEmpty()) return;

        var lamps = new net.mcreator.ssc.energy.LampTickBatch(level);
        for (EnergyNetwork network : networks) {
            network.resetTickStats();
            if (!network.isTopologyReady()) {
                if (type == CableType.LV) for (var pos : network.getConsumers()) lamps.track(pos);
                network.acknowledgeShutdown();
                continue;
            }
            long totalProducedJoules = 0;
            long totalMaxSupplyWatt = 0;
            long consumedJoules = 0;
            Map<BlockPos, Long> availableSources = new java.util.LinkedHashMap<>();

            Set<BlockPos> sourcePositions = network.getSources();
            Set<BlockPos> consumerPositions = network.getConsumers();

            for (var consumerIterator = consumerPositions.iterator(); consumerIterator.hasNext();) {
                BlockPos consumerPos = consumerIterator.next();
                if (!level.isLoaded(consumerPos)) continue;
                if (type == CableType.LV) {
                    if (!lamps.track(consumerPos)) consumerIterator.remove();
                    continue;
                }
                BlockEntity be = level.getBlockEntity(consumerPos);

                if (type == CableType.HV && !(be instanceof PodstationBlockEntity)) {
                    consumerIterator.remove();
                } else if (type == CableType.MV && !(be instanceof APCBlockEntity)) {
                    consumerIterator.remove();
                } else {
                    if (be instanceof PodstationBlockEntity podstation && type == CableType.HV) {
                        network.outputStored += podstation.getStoredEnergy();
                        network.outputMax += podstation.getMaxEnergy();
                    } else if (be instanceof APCBlockEntity apc && type == CableType.MV) {
                        network.outputStored += apc.getStoredEnergy();
                        network.outputMax += apc.getMaxEnergy();
                    }
                }
            }

            for (BlockPos sourcePos : sourcePositions) {
                if (!level.isLoaded(sourcePos)) continue;
                BlockState state = level.getBlockState(sourcePos);

                if (type == CableType.HV && state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                    totalProducedJoules += DEBUG_GEN_JOULES_PER_TICK;
                    availableSources.put(sourcePos, DEBUG_GEN_JOULES_PER_TICK);
                    totalMaxSupplyWatt += DEBUG_GEN_WATT;
                } else {
                    BlockEntity be = level.getBlockEntity(sourcePos);
                    if (type == CableType.MV && be instanceof PodstationBlockEntity podstation) {
                        network.inputStored += podstation.getStoredEnergy();
                        network.inputMax += podstation.getMaxEnergy();
                        long snapshotEnergy = energySnapshot.getOrDefault(new SourceKey(level, sourcePos), podstation.getStoredEnergy());
                        long potentialSupply = Math.min(snapshotEnergy, SUBSTATION_OUTPUT_WATT / TICKS_PER_SECOND);

                        totalMaxSupplyWatt += SUBSTATION_OUTPUT_WATT;
                        if (snapshotEnergy > 0) {
                            totalProducedJoules += potentialSupply;
                            availableSources.put(sourcePos, potentialSupply);

                        }
                    } else if (type == CableType.LV && be instanceof APCBlockEntity apc) {
                        network.inputStored += apc.getStoredEnergy();
                        network.inputMax += apc.getMaxEnergy();
                        if (apc.isMainBreakerEnabled()) {
                            long snapshotEnergy = energySnapshot.getOrDefault(new SourceKey(level, sourcePos), apc.getStoredEnergy());
                            long potentialSupply = Math.min(snapshotEnergy, APC_OUTPUT_WATT / TICKS_PER_SECOND);

                            totalMaxSupplyWatt += APC_OUTPUT_WATT;
                            if (snapshotEnergy > 0) {
                                totalProducedJoules += potentialSupply;
                                availableSources.put(sourcePos, potentialSupply);

                            }
                        } else {
                            apc.lastTickDischargingJoules = 0;
                        }
                    }
                }
            }

            if (type == CableType.LV) {
                network.outputStored = network.inputStored;
                network.outputMax = network.inputMax;
            }
            network.theoreticalSupply = totalMaxSupplyWatt;
            network.currentPower = totalProducedJoules * 20;
            if (type == CableType.LV) {
                long activeLampsCount = 0;
                for (BlockPos lampPos : consumerPositions) {
                    if (lamps.needsPower(lampPos)) activeLampsCount++;
                }
                long requiredJoulesForLamps = activeLampsCount * LAMP_JOULES_PER_TICK;
                network.idealConsumption = activeLampsCount * LAMP_WATT;
                boolean hasEnoughPower = totalProducedJoules >= requiredJoulesForLamps && requiredJoulesForLamps > 0;
                consumedJoules = hasEnoughPower ? requiredJoulesForLamps : 0;
                if (hasEnoughPower) for (BlockPos lampPos : consumerPositions) lamps.power(lampPos);
            } else {
                if (!consumerPositions.isEmpty()) {
                    long totalDemandJoules = 0;
                    for (BlockPos consumerPos : consumerPositions) {
                        if (!level.isLoaded(consumerPos)) continue;
                        BlockEntity be = level.getBlockEntity(consumerPos);

                        if (be instanceof PodstationBlockEntity podstation && type == CableType.HV) {
                            long spaceLeft = podstation.getMaxEnergy() - energySnapshot.getOrDefault(new SourceKey(level, consumerPos), podstation.getStoredEnergy());
                            totalDemandJoules += Math.min(MAX_DEVICE_INPUT_JOULES, spaceLeft);
                        } else if (be instanceof APCBlockEntity apc && type == CableType.MV) {
                            long spaceLeft = apc.getMaxEnergy() - energySnapshot.getOrDefault(new SourceKey(level, consumerPos), apc.getStoredEnergy());
                            totalDemandJoules += Math.min(MAX_DEVICE_INPUT_JOULES, spaceLeft);
                        }
                    }

                    network.idealConsumption = totalDemandJoules * 20;

                    if (totalProducedJoules > 0 && totalDemandJoules > 0) {
                        double supplyRatio = Math.min(1.0, (double) totalProducedJoules / totalDemandJoules);

                        for (BlockPos consumerPos : consumerPositions) {
                            if (!level.isLoaded(consumerPos)) continue;
                            BlockEntity be = level.getBlockEntity(consumerPos);

                            if (be instanceof PodstationBlockEntity podstation && type == CableType.HV) {
                                long spaceLeft = podstation.getMaxEnergy() - energySnapshot.getOrDefault(new SourceKey(level, consumerPos), podstation.getStoredEnergy());
                                long desired = Math.min(MAX_DEVICE_INPUT_JOULES, spaceLeft);
                                long actualInserted = (long) (desired * supplyRatio);
                                consumedJoules += actualInserted;

                                podstation.setStoredEnergySimulation(Math.min(podstation.getMaxEnergy(), podstation.getStoredEnergy() + actualInserted));
                            } else if (be instanceof APCBlockEntity apc && type == CableType.MV) {
                                long spaceLeft = apc.getMaxEnergy() - energySnapshot.getOrDefault(new SourceKey(level, consumerPos), apc.getStoredEnergy());
                                long desired = Math.min(MAX_DEVICE_INPUT_JOULES, spaceLeft);
                                long actualInserted = (long) (desired * supplyRatio);
                                consumedJoules += actualInserted;

                                apc.lastTickReceivingJoules = actualInserted;
                                apc.setStoredEnergySimulation(Math.min(apc.getMaxEnergy(), apc.getStoredEnergy() + actualInserted));
                            }
                        }
                    }
                }
            }

            if (consumedJoules > 0) {
                long[] debits = net.mcreator.ssc.math.ProportionalAllocation.allocate(consumedJoules,
                        availableSources.values().stream().mapToLong(Long::longValue).toArray());
                int sourceIndex = 0;
                for (BlockPos sourcePos : availableSources.keySet()) {
                    long debit = debits[sourceIndex++];
                    BlockEntity source = level.getBlockEntity(sourcePos);
                    if (source instanceof PodstationBlockEntity podstation) {
                        podstation.setStoredEnergySimulation(podstation.getStoredEnergy() - debit);
                        network.batteryPower += debit * 20;
                    } else if (source instanceof APCBlockEntity apc) {
                        apc.setStoredEnergySimulation(apc.getStoredEnergy() - debit);
                        apc.lastTickDischargingJoules += debit;
                        network.batteryPower += debit * 20;
                    }
                    SourceKey key = new SourceKey(level, sourcePos);
                    if (energySnapshot.containsKey(key)) energySnapshot.put(key, energySnapshot.get(key) - debit);
                }
            }
            network.currentPower = consumedJoules * 20;

            net.mcreator.ssc.energy.EnergyTelemetry.publish(level, network);
        }
        if (type == CableType.LV) lamps.apply();
    }
}
