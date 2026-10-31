package net.ssc14.client.overlay;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import org.junit.jupiter.api.Test;

class InteractionOverlayBoundaryTest {
    @Test
    void canonicalOverlaysOwnSubscriptionsAndLegacyFacadesKeepHandlerApi()
            throws ReflectiveOperationException {
        Class<?>[] canonicalOverlays = {
                ActionIndicatorOverlay.class,
                GunBulletCountUIOverlay.class,
                MultitulUIOverlay.class,
                HealthAnalyzerUIOverlay.class
        };
        Class<?>[] legacyOverlays = {
                net.mcreator.ssc.client.screens.ActionIndicatorOverlay.class,
                net.mcreator.ssc.client.screens.GunBulletCountUIOverlay.class,
                net.mcreator.ssc.client.screens.MultitulUIOverlay.class,
                net.mcreator.ssc.client.screens.HealthAnalyzerUIOverlay.class
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
