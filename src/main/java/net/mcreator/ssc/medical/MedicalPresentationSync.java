package net.mcreator.ssc.medical;

import net.mcreator.ssc.network.MedicalPresentationMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/** Server snapshots on transitions and client lifecycle initialization; no per-tick broadcast. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalPresentationSync {
    private MedicalPresentationSync() { }

    static void changed(Player player, boolean before, boolean after) {
        if (before != after && player instanceof ServerPlayer serverPlayer) {
            var message = snapshot(player);
            PacketDistributor.sendToPlayer(serverPlayer, message);
            PacketDistributor.sendToPlayersTrackingEntity(player, message);
        }
    }

    private static MedicalPresentationMessage snapshot(Player player) {
        return new MedicalPresentationMessage(player.getId(), player.getUUID(), player.level().dimension().identifier(),
                MedicalPlayerEffects.ownsPose(player));
    }

    private static void initialize(Player player) {
        if (player instanceof ServerPlayer serverPlayer) PacketDistributor.sendToPlayer(serverPlayer, snapshot(player));
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer tracker && event.getTarget() instanceof Player target) {
            PacketDistributor.sendToPlayer(tracker, snapshot(target));
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) { initialize(event.getEntity()); }
    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) { initialize(event.getEntity()); }
    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) { initialize(event.getEntity()); }
}
