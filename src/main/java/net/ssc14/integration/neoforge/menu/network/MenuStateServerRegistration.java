package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.core.network.MenuStateAccess;
import net.ssc14.core.network.MenuStateRules;
import net.ssc14.integration.neoforge.menu.MenuStateGuard;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public final class MenuStateServerRegistration {
    private MenuStateServerRegistration() { }

    public static void handle(MenuStateUpdateMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> {
            if (!(context.player().containerMenu instanceof MenuStateAccess menu)
                    || !MenuStateGuard.canEdit(context.player())
                    || !MenuStateRules.acceptsClientField(
                            context.player().containerMenu.getClass().getSimpleName(),
                            message.elementType(), message.name())) return;
            menu.getMenuState().put(message.elementType() + ":" + message.name(), message.elementState());
        }).exceptionally(error -> {
            context.connection().disconnect(Component.literal("Invalid menu state"));
            return null;
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        // The client handler is attached by the client-only payload-handler event.
        Ssc14NetworkPayloads.addBidirectional(MenuStateUpdateMessage.TYPE,
                MenuStateUpdateMessage.STREAM_CODEC, MenuStateServerRegistration::handle, null);
    }
}
