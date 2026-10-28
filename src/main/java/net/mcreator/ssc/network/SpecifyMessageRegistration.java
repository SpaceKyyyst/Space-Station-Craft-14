package net.mcreator.ssc.network;

import net.mcreator.ssc.procedures.Specify_CLICK_Procedure;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.player.network.SpecifyMessage;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public final class SpecifyMessageRegistration {
    private SpecifyMessageRegistration() { }

    public static void handle(SpecifyMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> pressAction(context.player(), message.eventType(), message.pressedms()))
                .exceptionally(error -> { context.connection().disconnect(Component.literal(error.getMessage())); return null; });
    }

    public static void pressAction(Player player, int type, int pressedMilliseconds) {
        if (!player.level().getChunkSource().hasChunk(SectionPos.blockToSectionCoord(player.getX()),
                SectionPos.blockToSectionCoord(player.getZ()))) return;
        if (type == 0) Specify_CLICK_Procedure.execute(player.level(), player);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(SpecifyMessage.TYPE, SpecifyMessage.STREAM_CODEC,
                SpecifyMessageRegistration::handle);
    }
}
