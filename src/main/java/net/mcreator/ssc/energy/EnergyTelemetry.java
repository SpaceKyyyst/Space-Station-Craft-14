package net.mcreator.ssc.energy;

import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;
import net.ssc14.integration.neoforge.energy.network.MultitoolDataPacket;
import net.ssc14.content.block.energy.SheathingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;

public final class EnergyTelemetry {
    private static final int UPDATE_INTERVAL_TICKS = 4;
    private static final int DISPLAY_RADIUS = 16;
    private EnergyTelemetry() { }
    public static void publish(ServerLevel level, EnergyNetwork network) {
        if (level.getGameTime() % UPDATE_INTERVAL_TICKS != 0) return;
        CableType type = network.getType();
        int currentLayerMode = switch (type) { case LV -> 1; case MV -> 2; case HV -> 3; };

        if (!network.getCables().isEmpty()) {
            BlockPos mainCablePos = network.getCables().iterator().next();
            if (level.isLoaded(mainCablePos) && level.getBlockEntity(mainCablePos) instanceof SheathingBlockEntity sheathingBe) {
                sheathingBe.setLayerNetworkData(currentLayerMode, network.currentPower, network.theoreticalSupply, network.idealConsumption, network.outputStored, network.outputMax);
            }
        }

        for (var player : level.players()) {
            var handItem = player.getMainHandItem();
            if (!handItem.is(net.mcreator.ssc.init.Ssc14ModItems.MULTITUL.get())) continue;
            int toolMode = (int) handItem.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                    net.minecraft.world.item.component.CustomData.EMPTY).copyTag().getDoubleOr("Mode", 1.0);
            if (toolMode != currentLayerMode || !network.hasCableWithin(player.blockPosition(), DISPLAY_RADIUS)) continue;
            PacketDistributor.sendToPlayer(player, new MultitoolDataPacket(
                    currentLayerMode, network.currentPower, network.batteryPower, network.theoreticalSupply,
                    network.idealConsumption, network.inputStored, network.inputMax, network.outputStored, network.outputMax));
        }
    }
}
