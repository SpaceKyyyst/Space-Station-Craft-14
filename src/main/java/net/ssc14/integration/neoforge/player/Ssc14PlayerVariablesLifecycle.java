package net.ssc14.integration.neoforge.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/** Synchronizes and clones the SSC14 player attachment. */
@EventBusSubscriber(modid = "ssc_14")
public final class Ssc14PlayerVariablesLifecycle {
    private Ssc14PlayerVariablesLifecycle() { }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            synchronizeDimension(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawned(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            synchronizeDimension(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            synchronizeDimension(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        Ssc14PlayerVariables variables = player.getData(Ssc14PlayerAttachments.PLAYER_VARIABLES);
        if (!variables.isSyncDirty()) {
            return;
        }
        PacketDistributor.sendToPlayersInDimension(
                player.level(), new Ssc14PlayerVariablesSyncPayload(variables, player.getId()));
        variables.clearSyncDirty();
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        Ssc14PlayerVariables clone = new Ssc14PlayerVariables();
        if (!event.isWasDeath()) {
            clone.copyPersistentStateFrom(
                    event.getOriginal().getData(Ssc14PlayerAttachments.PLAYER_VARIABLES));
        }
        event.getEntity().setData(Ssc14PlayerAttachments.PLAYER_VARIABLES, clone);
    }

    private static void synchronizeDimension(ServerPlayer player) {
        for (Entity entity : player.level().players()) {
            if (entity != player && entity instanceof ServerPlayer otherPlayer) {
                PacketDistributor.sendToPlayer(
                        player,
                        new Ssc14PlayerVariablesSyncPayload(
                                otherPlayer.getData(Ssc14PlayerAttachments.PLAYER_VARIABLES),
                                otherPlayer.getId()));
            }
        }
        PacketDistributor.sendToPlayersInDimension(
                player.level(),
                new Ssc14PlayerVariablesSyncPayload(
                        player.getData(Ssc14PlayerAttachments.PLAYER_VARIABLES),
                        player.getId()));
    }
}
