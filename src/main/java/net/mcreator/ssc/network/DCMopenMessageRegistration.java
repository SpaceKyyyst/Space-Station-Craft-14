package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.DCM_OpenProcedure;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.player.network.DCMopenMessage;

@EventBusSubscriber
public final class DCMopenMessageRegistration {
    private DCMopenMessageRegistration() { }

    public static void handle(DCMopenMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> pressAction(context.player(), message.eventType(), message.pressedms()))
                .exceptionally(error -> { context.connection().disconnect(Component.literal(error.getMessage())); return null; });
    }

    public static void pressAction(Player player, int type, int pressedMilliseconds) {
        var level = player.level();
        if (!level.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(player.getX()),
                SectionPos.blockToSectionCoord(player.getZ()))) return;
        if (type == 0) DCM_OpenProcedure.execute(level, player.getX(), player.getY(), player.getZ(), player);
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14Mod.addServerboundNetworkMessage(DCMopenMessage.TYPE, DCMopenMessage.STREAM_CODEC,
                DCMopenMessageRegistration::handle);
    }
}
