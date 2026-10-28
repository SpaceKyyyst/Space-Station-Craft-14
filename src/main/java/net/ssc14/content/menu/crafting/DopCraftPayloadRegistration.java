package net.ssc14.content.menu.crafting;

import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;
import net.ssc14.integration.neoforge.player.network.DCMopenMessage;

/** Registers and dispatches the additional-crafting menu key payload. */
@EventBusSubscriber
public final class DopCraftPayloadRegistration {
    private DopCraftPayloadRegistration() { }

    public static void handle(DCMopenMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> dispatch(context.player(), message.eventType()))
                .exceptionally(error -> {
                    context.connection().disconnect(Component.literal(error.getMessage()));
                    return null;
                });
    }

    public static void dispatch(Player player, int eventType) {
        if (!player.level().getChunkSource().hasChunk(
                SectionPos.blockToSectionCoord(player.getX()),
                SectionPos.blockToSectionCoord(player.getZ()))) return;
        if (eventType == 0) DopCraftMenuService.toggle(player);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(DCMopenMessage.TYPE,
                DCMopenMessage.STREAM_CODEC, DopCraftPayloadRegistration::handle);
    }
}
