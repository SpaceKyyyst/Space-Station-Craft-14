package net.ssc14.client.menu;

import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;

@EventBusSubscriber(value = Dist.CLIENT)
public final class MenuStateClientRegistration {
    private MenuStateClientRegistration() { }

    public static void handle(MenuStateUpdateMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.CLIENTBOUND) return;
        context.enqueueWork(() -> MenuStateClientHandler.apply(message));
    }

    @SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {
        event.register(MenuStateUpdateMessage.TYPE, MenuStateClientRegistration::handle);
    }
}
