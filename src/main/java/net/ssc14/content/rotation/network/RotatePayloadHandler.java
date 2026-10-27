package net.ssc14.content.rotation.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.content.rotation.RotateCommand;
import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;
import net.ssc14.integration.neoforge.rotation.network.RotateMessage;

/** Authoritative server handler for the rotation key payload. */
@EventBusSubscriber(modid = "ssc_14")
public final class RotatePayloadHandler {
    private static final String PROTOCOL_VERSION = "ssc_14";

    private RotatePayloadHandler() { }

    public static void handle(RotateMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND
                && context.player() instanceof ServerPlayer player) {
            RotateCommand.execute(player, message.eventType());
        }
    }

    public static void pressAction(Player entity, int type, int pressedMilliseconds) {
        if (entity instanceof ServerPlayer player) RotateCommand.execute(player, type);
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = new CompatibilityPayloadRegistrar(
                event.registrar(PROTOCOL_VERSION), PROTOCOL_VERSION);
        registrar.playToServer(RotateMessage.TYPE, RotateMessage.STREAM_CODEC,
                RotatePayloadHandler::handle);
    }
}
