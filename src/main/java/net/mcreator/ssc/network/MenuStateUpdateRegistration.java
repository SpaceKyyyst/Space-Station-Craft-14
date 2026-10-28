package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.init.Ssc14ModMenus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.client.menu.MenuStateClientHandler;
import net.ssc14.core.network.MenuStateRules;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

/** Transitional menu adapter until generated menu accessors move to the target namespace. */
@EventBusSubscriber
public final class MenuStateUpdateRegistration {
    private MenuStateUpdateRegistration() { }

    public static void handle(MenuStateUpdateMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player().containerMenu instanceof Ssc14ModMenus.MenuAccessor menu)) return;
            if (context.flow() == PacketFlow.SERVERBOUND
                    && (!LegacyMenuStateGuard.canEdit(context.player())
                    || !MenuStateRules.acceptsClientField(
                            context.player().containerMenu.getClass().getSimpleName(),
                            message.elementType(), message.name()))) return;
            menu.getMenuState().put(message.elementType() + ":" + message.name(), message.elementState());
            if (context.flow() == PacketFlow.CLIENTBOUND) MenuStateClientHandler.apply(message);
        }).exceptionally(error -> {
            Ssc14Mod.LOGGER.error("Failed to apply menu state", error);
            context.connection().disconnect(Component.literal("Invalid menu state"));
            return null;
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addBidirectional(MenuStateUpdateMessage.TYPE,
                MenuStateUpdateMessage.STREAM_CODEC, MenuStateUpdateRegistration::handle);
    }
}
