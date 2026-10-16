package net.mcreator.ssc;

import net.mcreator.ssc.block.entity.SheathingBlockEntity;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.network.PacketDistributor;

/** Adapts network measurements to the existing cable NBT and multitool packet formats. */
final class EnergyNetworkTelemetry {
    private static final int MULTITOOL_RADIUS = 16;

    private EnergyNetworkTelemetry() {
    }

    static void publish(ServerLevel level, EnergyNetwork network) {
        int layer = switch (network.getType()) { case LV -> 1; case MV -> 2; case HV -> 3; };
        for (BlockPos pos : network.getCables()) {
            if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof SheathingBlockEntity cable) {
                cable.setLayerNetworkData(layer, network.currentPower, network.theoreticalSupply,
                        network.idealConsumption, network.outputStored, network.outputMax);
                break;
            }
        }
        for (ServerPlayer player : level.players()) {
            ItemStack item = player.getMainHandItem();
            if (!item.is(Ssc14ModItems.MULTITUL.get())) {
                continue;
            }
            int mode = (int) item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag().getDoubleOr("Mode", 1.0);
            if (mode != layer) {
                continue;
            }
            for (BlockPos pos : network.getCables()) {
                if (player.blockPosition().closerThan(pos, MULTITOOL_RADIUS)) {
                    PacketDistributor.sendToPlayer(player, new MultitoolDataPacket(layer,
                            network.currentPower, network.batteryPower, network.theoreticalSupply,
                            network.idealConsumption, network.inputStored, network.inputMax,
                            network.outputStored, network.outputMax));
                    break;
                }
            }
        }
    }
}
