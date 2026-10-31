package net.ssc14.client.overlay;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import org.junit.jupiter.api.Test;

class HungerThirstOverlayBoundaryTest {
    @Test
    void canonicalOverlaysOwnSubscriptionsAndLegacyFacadesKeepHandlerApi()
            throws ReflectiveOperationException {
        Class<?>[] canonicalOverlays = {
                Hunger0Overlay.class,
                Hunger1Overlay.class,
                Hunger2Overlay.class,
                Thirst0Overlay.class,
                Thirst1Overlay.class,
                Thirst2Overlay.class
        };
        Class<?>[] legacyOverlays = {
                net.mcreator.ssc.client.screens.Hunger0Overlay.class,
                net.mcreator.ssc.client.screens.Hunger1Overlay.class,
                net.mcreator.ssc.client.screens.Hunger2Overlay.class,
                net.mcreator.ssc.client.screens.Thirst0Overlay.class,
                net.mcreator.ssc.client.screens.Thirst1Overlay.class,
                net.mcreator.ssc.client.screens.Thirst2Overlay.class
        };

        for (int index = 0; index < canonicalOverlays.length; index++) {
            Method canonicalHandler = canonicalOverlays[index]
                    .getMethod("eventHandler", RenderGuiEvent.Pre.class);
            Method legacyHandler = legacyOverlays[index]
                    .getMethod("eventHandler", RenderGuiEvent.Pre.class);

            assertNotNull(canonicalOverlays[index].getAnnotation(EventBusSubscriber.class));
            assertNotNull(canonicalHandler.getAnnotation(SubscribeEvent.class));
            assertTrue(Modifier.isStatic(legacyHandler.getModifiers()));
        }
    }
}
