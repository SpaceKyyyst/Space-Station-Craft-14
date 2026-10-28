package net.ssc14.content.player.action;

import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;
import net.ssc14.integration.neoforge.player.network.PulltheObjectMessage;
import net.ssc14.integration.neoforge.player.network.SpecifyMessage;
import net.ssc14.integration.neoforge.player.network.WorldObjectCheckMessage;

/** Canonical registration and authoritative dispatch for player action payloads. */
@EventBusSubscriber
public final class PlayerActionPayloadRegistration {
    private PlayerActionPayloadRegistration() { }

    public static void handlePull(PulltheObjectMessage message, IPayloadContext context) {
        enqueueServerbound(context, () -> dispatchPull(context.player(), message.eventType()));
    }

    public static void handleSpecify(SpecifyMessage message, IPayloadContext context) {
        enqueueServerbound(context, () -> dispatchSpecify(context.player(), message.eventType()));
    }

    public static void handleWorldCheck(WorldObjectCheckMessage message, IPayloadContext context) {
        enqueueServerbound(context, () -> dispatchWorldCheck(context.player(), message.eventType()));
    }

    private static void enqueueServerbound(IPayloadContext context, Runnable action) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(action).exceptionally(error -> {
            context.connection().disconnect(Component.literal(error.getMessage()));
            return null;
        });
    }

    public static void dispatchPull(Player player, int eventType) {
        if (!isPlayerChunkLoaded(player)) return;
        if (eventType == 0) PlayerActionStateService.setPulling(player, true);
        if (eventType == 1) PlayerActionStateService.setPulling(player, false);
    }

    public static void dispatchSpecify(Player player, int eventType) {
        if (!isPlayerChunkLoaded(player)) return;
        if (eventType == 0) SpecifyTargetService.mark(player);
    }

    public static void dispatchWorldCheck(Player player, int eventType) {
        if (!isPlayerChunkLoaded(player)) return;
        if (eventType == 0) PlayerActionStateService.setWorldCheckOpen(player, true);
        if (eventType == 1) PlayerActionStateService.setWorldCheckOpen(player, false);
    }

    private static boolean isPlayerChunkLoaded(Player player) {
        return player.level().getChunkSource().hasChunk(
                SectionPos.blockToSectionCoord(player.getX()),
                SectionPos.blockToSectionCoord(player.getZ()));
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(PulltheObjectMessage.TYPE,
                PulltheObjectMessage.STREAM_CODEC, PlayerActionPayloadRegistration::handlePull);
        Ssc14NetworkPayloads.addServerbound(SpecifyMessage.TYPE,
                SpecifyMessage.STREAM_CODEC, PlayerActionPayloadRegistration::handleSpecify);
        Ssc14NetworkPayloads.addServerbound(WorldObjectCheckMessage.TYPE,
                WorldObjectCheckMessage.STREAM_CODEC, PlayerActionPayloadRegistration::handleWorldCheck);
    }
}
