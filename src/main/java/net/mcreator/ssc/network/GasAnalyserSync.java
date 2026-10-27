package net.mcreator.ssc.network;

import net.ssc14.content.atmosphere.runtime.AtmosphereManager;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "ssc_14")
public final class GasAnalyserSync {
    private static final int UPDATE_INTERVAL_TICKS = 5;
    private GasAnalyserSync() { }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || player.tickCount % UPDATE_INTERVAL_TICKS != 0
                || !GasAnalyserOverlayService.isVisible(player)) return;
        var position = player.blockPosition();
        var cell = AtmosphereManager.get(player.level()).readCellAt(position);
        if (cell != null) PacketDistributor.sendToPlayer(player, GasAnalyserData.from(player.level().dimension().identifier().toString(), position, cell));
    }
}
