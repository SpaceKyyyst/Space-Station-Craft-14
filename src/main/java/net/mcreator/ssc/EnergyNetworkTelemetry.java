package net.mcreator.ssc;

import net.mcreator.ssc.block.entity.SheathingBlockEntity;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.PacketDistributor;

/** Adapts network measurements to the existing cable NBT and multitool packet formats. */
final class EnergyNetworkTelemetry {
    private static final int UPDATE_INTERVAL_TICKS = 4;
    private static final double MULTITOOL_REACH = 5.0;

    private EnergyNetworkTelemetry() {
    }

    static void publishCableData(ServerLevel level, EnergyNetwork network) {
        if (level.getGameTime() % UPDATE_INTERVAL_TICKS != 0) return;
        int layer = switch (network.getType()) { case LV -> 1; case MV -> 2; case HV -> 3; };
        for (BlockPos pos : network.getCables()) {
            if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof SheathingBlockEntity cable) {
                cable.setLayerNetworkData(layer, network.currentPower, network.theoreticalSupply,
                        network.idealConsumption, network.outputStored, network.outputMax);
                break;
            }
        }
    }

    static void publishToolData(ServerLevel level) {
        if (level.getGameTime() % UPDATE_INTERVAL_TICKS != 0) return;
        for (ServerPlayer player : level.players()) {
            ItemStack item = player.getMainHandItem();
            if (!item.is(Ssc14ModItems.MULTITUL.get())) {
                continue;
            }
            int mode = (int) item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag().getDoubleOr("Mode", 1.0);
            if (mode < 1 || mode > 3) {
                mode = 1;
            }
            HitResult hit = player.pick(MULTITOOL_REACH, 1.0F, false);
            if (!(hit instanceof BlockHitResult blockHit) || hit.getType() != HitResult.Type.BLOCK) {
                continue;
            }
            CableType type = switch (mode) { case 2 -> CableType.MV; case 3 -> CableType.HV; default -> CableType.LV; };
            var network = net.mcreator.ssc.energy.EnergyTopologyService.networkAt(level, type, blockHit.getBlockPos());
            if (network != null) {
                PacketDistributor.sendToPlayer(player, new MultitoolDataPacket(mode,
                        network.currentPower, network.batteryPower, network.theoreticalSupply,
                        network.idealConsumption, network.inputStored, network.inputMax,
                        network.outputStored, network.outputMax));
            }
            else {
                PacketDistributor.sendToPlayer(player, new MultitoolDataPacket(mode, 0, 0, 0, 0, 0, 0, 0, 0));
            }
        }
    }
}
