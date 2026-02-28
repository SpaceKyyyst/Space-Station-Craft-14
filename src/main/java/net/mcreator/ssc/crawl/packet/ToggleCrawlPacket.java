package net.mcreator.ssc.crawl.packet;
import net.mcreator.ssc.crawl.CrawlSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
public record ToggleCrawlPacket() implements CustomPacketPayload {
    public static final Type<ToggleCrawlPacket> TYPE = new Type<>(PacketHandler.TOGGLE_CRAWL_ID);
    public static final StreamCodec<FriendlyByteBuf, ToggleCrawlPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {},
            buf -> new ToggleCrawlPacket()
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player == null) return;

            if (CrawlSystem.isCrawling(player)) {
                CrawlSystem.stopCrawling(player);
            } else {
                if (player.isPassenger() || player.isSleeping()) return;
                CrawlSystem.startCrawling(player);
            }
        });
    }
}