package net.mcreator.ssc.client;

import com.ssc14.handy.client.gui.ClientGuiModeEvents;
import com.ssc14.handy.client.gui.GuiMode;
import com.ssc14.handy.window.WindowManager;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;
import java.util.Map;
import java.util.List;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Remove when Handy fixes unconditional cancellation of vanilla mouse input. */
@EventBusSubscriber(modid = Ssc14Mod.MODID, value = Dist.CLIENT)
public final class HandyMouseCompatibility {
    private static Map<Integer, MouseButtonInfo> presses;
    private HandyMouseCompatibility() { }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            String version = ModList.get().getModContainerById("handy").orElseThrow().getModInfo().getVersion().toString();
            if (!"0.1.0".equals(version)) return;
            // FML registers each annotated Method, not its declaring Class.
            // Keep Handy's render-time movement handling, sharing its pressed-button state.
            try {
                var mouseHandler = ClientGuiModeEvents.class.getDeclaredMethod("onMouseButton", InputEvent.MouseButton.Pre.class);
                NeoForge.EVENT_BUS.unregister(mouseHandler);
                var field = ClientGuiModeEvents.class.getDeclaredField("GUI_PRESSES");
                field.setAccessible(true);
                @SuppressWarnings("unchecked")
                var held = (Map<Integer, MouseButtonInfo>) field.get(null);
                presses = held;
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Handy 0.1.0 mouse handler changed; review compatibility adapter", exception);
            }
            NeoForge.EVENT_BUS.addListener(HandyMouseCompatibility::onMouseButton);
            NeoForge.EVENT_BUS.addListener(HandyMouseCompatibility::onTick);
            Ssc14Mod.LOGGER.info("Enabled Handy 0.1.0 mouse input compatibility");
        });
    }

    private static void onMouseButton(InputEvent.MouseButton.Pre event) {
        if (event.getAction() == GLFW.GLFW_RELEASE && presses.remove(event.getButton()) != null) {
            WindowManager.INSTANCE.mouseReleased(mouseEvent(event.getMouseButtonInfo()));
            event.setCanceled(true);
            return;
        }
        if (GuiMode.isActive() && Minecraft.getInstance().screen == null) {
            ClientGuiModeEvents.onMouseButton(event);
        }
    }

    private static MouseButtonEvent mouseEvent(MouseButtonInfo button) {
        var minecraft = Minecraft.getInstance();
        return new MouseButtonEvent(minecraft.mouseHandler.getScaledXPos(minecraft.getWindow()),
                minecraft.mouseHandler.getScaledYPos(minecraft.getWindow()), button);
    }

    private static void onTick(ClientTickEvent.Post event) {
        var minecraft = Minecraft.getInstance();
        boolean interactive = minecraft.player != null && minecraft.level != null
                && minecraft.screen == null && WindowManager.INSTANCE.getFocusedWindow() != null;
        if (interactive) {
            GuiMode.enter();
        } else {
            for (var button : List.copyOf(presses.values())) {
                if (minecraft.player != null) WindowManager.INSTANCE.mouseReleased(mouseEvent(button));
            }
            presses.clear();
            GuiMode.exit();
        }
    }
}
