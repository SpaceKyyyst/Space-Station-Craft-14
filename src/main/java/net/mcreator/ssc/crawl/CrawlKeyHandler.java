package net.mcreator.ssc.crawl;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.crawl.packet.PacketHandler;
import net.mcreator.ssc.crawl.packet.ToggleCrawlPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
@EventBusSubscriber(modid = Ssc14Mod.MODID, value = Dist.CLIENT)
public class CrawlKeyHandler {
    public static final KeyMapping CRAWL_KEY = new KeyMapping(
            "key.ssc14.crawl",
            GLFW.GLFW_KEY_Z,
            "key.categories.ssc14"
    );
    private static boolean wasPressed = false;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        boolean isPressed = CRAWL_KEY.isDown();
        if (isPressed && !wasPressed) {
            PacketHandler.sendToServer(new ToggleCrawlPacket());
        }
        wasPressed = isPressed;
    }
}
@EventBusSubscriber(modid = Ssc14Mod.MODID, value = Dist.CLIENT)
class CrawlKeyRegister {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(CrawlKeyHandler.CRAWL_KEY);
    }
}