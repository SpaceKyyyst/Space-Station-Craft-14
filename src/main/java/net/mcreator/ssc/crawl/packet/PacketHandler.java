package net.mcreator.ssc.crawl.packet;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
public class PacketHandler {
    public static final ResourceLocation TOGGLE_CRAWL_ID =
            ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "toggle_crawl");
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Ssc14Mod.MODID)
                .versioned("1.0");
        registrar.playToServer(
                new CustomPacketPayload.Type<>(TOGGLE_CRAWL_ID),
                ToggleCrawlPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(context)
        );
    }
    public static void sendToServer(CustomPacketPayload packet) {
        if (net.neoforged.fml.loading.FMLEnvironment.dist.isClient()) {
            net.minecraft.client.Minecraft.getInstance().getConnection().send(packet);
        }
    }
}