package net.ssc14.content.player.crawl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;
import net.ssc14.integration.neoforge.player.network.CrawlMessage;

/** Serverbound crawling payload registration and dispatch. */
@EventBusSubscriber
public final class CrawlPayloadRegistration {
    private CrawlPayloadRegistration() { }

    public static void handle(CrawlMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> CrawlService.pressAction(
                context.player(), message.eventType(), message.pressedms()))
                .exceptionally(error -> {
                    context.connection().disconnect(Component.literal(error.getMessage()));
                    return null;
                });
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        Ssc14NetworkPayloads.addServerbound(CrawlMessage.TYPE,
                CrawlMessage.STREAM_CODEC, CrawlPayloadRegistration::handle);
    }
}
