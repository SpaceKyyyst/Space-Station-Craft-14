package net.mcreator.ssc.network;

import net.mcreator.ssc.procedures.WorldObjectCheck_Pr_2Procedure;
import net.mcreator.ssc.procedures.WorldObjectCheck_PrProcedure;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.player.network.WorldObjectCheckMessage;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public final class WorldObjectCheckMessageRegistration {
    private WorldObjectCheckMessageRegistration() { }

    public static void handle(WorldObjectCheckMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> pressAction(context.player(), message.eventType(), message.pressedms()))
                .exceptionally(error -> { context.connection().disconnect(Component.literal(error.getMessage())); return null; });
    }

    public static void pressAction(Player player, int type, int pressedMilliseconds) {
        if (!player.level().getChunkSource().hasChunk(SectionPos.blockToSectionCoord(player.getX()),
                SectionPos.blockToSectionCoord(player.getZ()))) return;
        if (type == 0) WorldObjectCheck_PrProcedure.execute(player);
        if (type == 1) WorldObjectCheck_Pr_2Procedure.execute(player);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(WorldObjectCheckMessage.TYPE,
                WorldObjectCheckMessage.STREAM_CODEC, WorldObjectCheckMessageRegistration::handle);
    }
}
