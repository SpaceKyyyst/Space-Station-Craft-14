
package net.mcreator.ssc;

import net.mcreator.ssc.block.entity.PodstationBlockEntity;
import net.mcreator.ssc.block.entity.APCBlockEntity;
import net.mcreator.ssc.block.entity.SheathingBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@EventBusSubscriber
public class EnergySimulationServer {

    private static final long DEBUG_GEN_WATT = 100000;
    private static final long DEBUG_GEN_JOULES_PER_TICK = DEBUG_GEN_WATT / 20;
    private static final long MAX_DEVICE_INPUT_JOULES = 50000 / 20;
    private static final long LAMP_WATT = 12;
    private static final long LAMP_JOULES_PER_TICK = 1;

    private static final Map<BlockPos, Long> energySnapshot = new HashMap<>();

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        energySnapshot.clear();

        for (ServerLevel serverLevel : event.getServer().getAllLevels()) {
            for (CableType type : CableType.values()) {
                List<EnergyNetwork> networks = EnergyNetworkManager.getNetworks(serverLevel, type);
                for (EnergyNetwork network : networks) {
                    
                    // Динамическая очистка мёртвых источников
                    Iterator<BlockPos> sourceIterator = network.getSources().iterator();
                    while (sourceIterator.hasNext()) {
                        BlockPos pos = sourceIterator.next();
                        if (!serverLevel.isLoaded(pos)) continue;
                        
                        BlockEntity be = serverLevel.getBlockEntity(pos);
                        BlockState state = serverLevel.getBlockState(pos);
                        
                        if (type == CableType.HV && !state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                            sourceIterator.remove(); // Генератор сломали
                        } else if (type == CableType.MV && !(be instanceof PodstationBlockEntity)) {
                            sourceIterator.remove(); // Подстанцию сломали
                        } else if (type == CableType.LV && !(be instanceof APCBlockEntity)) {
                            sourceIterator.remove(); // ЛКП сломали
                        } else if (!energySnapshot.containsKey(pos)) {
                            if (be instanceof PodstationBlockEntity podstation) {
                                energySnapshot.put(pos, podstation.getStoredEnergy());
                            } else if (be instanceof APCBlockEntity apc) {
                                energySnapshot.put(pos, apc.getStoredEnergy());
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
    }
    private static void simulateNetworkLayer(ServerLevel level, CableType type) {
        List<EnergyNetwork> networks = EnergyNetworkManager.getNetworks(level, type);
        if (networks.isEmpty()) return;

        for (EnergyNetwork network : networks) {
            network.resetTickStats();
            long totalProducedJoules = 0;

            // Сбор статистики источников
            for (BlockPos sourcePos : network.getSources()) {
                if (!level.isLoaded(sourcePos)) continue;
                BlockEntity be = level.getBlockEntity(sourcePos);
                if (be instanceof PodstationBlockEntity podstation && type == CableType.MV) {
                    network.inputStored += podstation.getStoredEnergy();
                    network.inputMax += podstation.getMaxEnergy();
                } else if (be instanceof APCBlockEntity apc && type == CableType.LV) {
                    network.inputStored += apc.getStoredEnergy();
                    network.inputMax += apc.getMaxEnergy();
                }
            }

            // Сбор статистики потребителей + очистка сломанных блоков
            Iterator<BlockPos> consumerValidIterator = network.getConsumers().iterator();
            while (consumerValidIterator.hasNext()) {
                BlockPos consumerPos = consumerValidIterator.next();
                if (!level.isLoaded(consumerPos)) continue;
                
                BlockState state = level.getBlockState(consumerPos);
                BlockEntity be = level.getBlockEntity(consumerPos);

                if (type == CableType.HV && !(be instanceof PodstationBlockEntity)) {
                    consumerValidIterator.remove();
                } else if (type == CableType.MV && !(be instanceof APCBlockEntity)) {
                    consumerValidIterator.remove();
                } else if (type == CableType.LV && !state.is(Ssc14ModBlocks.LAMP.get())) {
                    consumerValidIterator.remove();
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

            if (type == CableType.LV) {
                network.outputStored = network.inputStored;
                network.outputMax = network.inputMax;
            }

            // Сбор энергии из источников
            for (BlockPos sourcePos : network.getSources()) {
                if (!level.isLoaded(sourcePos)) continue;
                
                BlockState state = level.getBlockState(sourcePos);
                if (type == CableType.HV && state.is(Ssc14ModBlocks.DEBU_GGENERATOR.get())) {
                    totalProducedJoules += DEBUG_GEN_JOULES_PER_TICK;
                    network.theoreticalSupply += DEBUG_GEN_WATT;
                } else {
                    BlockEntity be = level.getBlockEntity(sourcePos);
                    if (type == CableType.MV && be instanceof PodstationBlockEntity podstation) {
                        long snapshotEnergy = energySnapshot.getOrDefault(sourcePos, podstation.getStoredEnergy());
                        long potentialSupply = Math.min(snapshotEnergy, 150000 / 20);
                        network.theoreticalSupply += potentialSupply * 20;
                        if (snapshotEnergy > 0) {
                            totalProducedJoules += potentialSupply;
                            network.batteryPower += potentialSupply * 20;
                            podstation.setStoredEnergySimulation(Math.max(0, podstation.getStoredEnergy() - potentialSupply));
                        }
                    } else if (type == CableType.LV && be instanceof APCBlockEntity apc) {
                        long snapshotEnergy = energySnapshot.getOrDefault(sourcePos, apc.getStoredEnergy());
                        long potentialSupply = Math.min(snapshotEnergy, 15000 / 20);
                        network.theoreticalSupply += potentialSupply * 20;
                        if (snapshotEnergy > 0) {
                            totalProducedJoules += potentialSupply;
                            network.batteryPower += potentialSupply * 20;
                            apc.setStoredEnergySimulation(Math.max(0, apc.getStoredEnergy() - potentialSupply));
                        }
                    }
                }
            }

            network.currentPower = totalProducedJoules * 20;

            // Распределение потребителям
            if (type == CableType.LV) {
                long activeLampsCount = 0;
                for (BlockPos lampPos : network.getConsumers()) {
                    if (level.isLoaded(lampPos)) {
                        BlockState lampState = level.getBlockState(lampPos);
                        if (lampState.is(Ssc14ModBlocks.LAMP.get()) && lampState.getValue(net.mcreator.ssc.block.LampBlock.HAVE_LAMP) && !lampState.getValue(net.mcreator.ssc.block.LampBlock.BROKEN)) {
                            activeLampsCount++;
                        }
                    }
                }

                long requiredJoulesForLamps = activeLampsCount * LAMP_JOULES_PER_TICK;
                network.idealConsumption = activeLampsCount * LAMP_WATT;
                boolean hasEnoughPower = totalProducedJoules >= requiredJoulesForLamps && requiredJoulesForLamps > 0;

                for (BlockPos lampPos : network.getConsumers()) {
                    if (!level.isLoaded(lampPos)) continue;
                    BlockState lampState = level.getBlockState(lampPos);
                    if (lampState.is(Ssc14ModBlocks.LAMP.get())) {
                        boolean canLightUp = lampState.getValue(net.mcreator.ssc.block.LampBlock.HAVE_LAMP) && !lampState.getValue(net.mcreator.ssc.block.LampBlock.BROKEN) && hasEnoughPower;
                        if (lampState.getValue(net.mcreator.ssc.block.LampBlock.ACTIVE) != canLightUp) {
                            level.setBlock(lampPos, lampState.setValue(net.mcreator.ssc.block.LampBlock.ACTIVE, canLightUp), 3);
                        }
                    }
                }
            } else {
                if (!network.getConsumers().isEmpty() && totalProducedJoules > 0) {
                    long energyPerConsumer = totalProducedJoules / network.getConsumers().size();
                    for (BlockPos consumerPos : network.getConsumers()) {
                        if (!level.isLoaded(consumerPos)) continue;
                        BlockEntity be = level.getBlockEntity(consumerPos);
                        if (be instanceof PodstationBlockEntity podstation && type == CableType.HV) {
                            long snapshotEnergy = energySnapshot.getOrDefault(consumerPos, podstation.getStoredEnergy());
                            long spaceLeft = podstation.getMaxEnergy() - snapshotEnergy;
                            network.idealConsumption += Math.min(5000, spaceLeft * 20);
                            long actualInserted = Math.min(podstation.getMaxEnergy() - podstation.getStoredEnergy(), Math.min(energyPerConsumer, MAX_DEVICE_INPUT_JOULES));
                            podstation.setStoredEnergySimulation(Math.min(podstation.getMaxEnergy(), podstation.getStoredEnergy() + actualInserted));
                        } else if (be instanceof APCBlockEntity apc && type == CableType.MV) {
                            long snapshotEnergy = energySnapshot.getOrDefault(consumerPos, apc.getStoredEnergy());
                            long spaceLeft = apc.getMaxEnergy() - snapshotEnergy;
                            network.idealConsumption += Math.min(5000, spaceLeft * 20);
                            long actualInserted = Math.min(apc.getMaxEnergy() - apc.getStoredEnergy(), Math.min(energyPerConsumer, MAX_DEVICE_INPUT_JOULES));
                            apc.setStoredEnergySimulation(Math.min(apc.getMaxEnergy(), apc.getStoredEnergy() + actualInserted));
                        }
                    }
                }
            }

            int currentLayerMode = switch (type) { case LV -> 1; case MV -> 2; case HV -> 3; };
            
            if (!network.getCables().isEmpty()) {
                BlockPos mainCablePos = network.getCables().iterator().next();
                if (level.isLoaded(mainCablePos) && level.getBlockEntity(mainCablePos) instanceof SheathingBlockEntity sheathingBe) {
                    sheathingBe.setLayerNetworkData(currentLayerMode, network.currentPower, network.theoreticalSupply, network.idealConsumption, network.outputStored, network.outputMax);
                }
            }

            for (net.minecraft.server.level.ServerPlayer player : level.players()) {
                net.minecraft.world.item.ItemStack handItem = player.getMainHandItem();
                if (handItem.is(net.mcreator.ssc.init.Ssc14ModItems.MULTITUL.get())) {
                    int toolMode = (int) handItem.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.EMPTY).copyTag().getDoubleOr("Mode", 1.0);
                    
                    if (toolMode == currentLayerMode) {
                        BlockPos pPos = player.blockPosition();
                        for (BlockPos cablePos : network.getCables()) {
                            if (pPos.closerThan(cablePos, 16)) {
                                PacketDistributor.sendToPlayer(player, new MultitoolDataPacket(
                                    currentLayerMode, network.currentPower, network.batteryPower, network.theoreticalSupply,
									network.idealConsumption, network.inputStored, network.inputMax, network.outputStored, 
									network.outputMax));
									break;
							}
						}
					}
				}
			}
		}
	}
}
