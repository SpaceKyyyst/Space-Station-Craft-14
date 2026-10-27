package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.network.NetConfigGUISyncMessage;

/** Transitional menu handler until the network configurator GUI moves to the target namespace. */
@EventBusSubscriber
public final class NetConfigGUISyncRegistration {
    private NetConfigGUISyncRegistration() { }

    public static void handle(NetConfigGUISyncMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.CLIENTBOUND) return;
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof NetConfigGUIMenu menu) {
                menu.menuState.clear();
                menu.menuState.putAll(message.state());
            }
        }).exceptionally(error -> {
            Ssc14Mod.LOGGER.error("Failed to apply network configurator state", error);
            context.connection().disconnect(Component.literal("Invalid network configurator state"));
            return null;
        });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14Mod.addClientboundNetworkMessage(NetConfigGUISyncMessage.TYPE,
                NetConfigGUISyncMessage.STREAM_CODEC, NetConfigGUISyncRegistration::handle);
    }
}
