package net.ssc14.client.overlay;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.junit.jupiter.api.Test;

class GasAnalyserOverlayBoundaryTest {
    @Test
    void canonicalOverlayOwnsClientSubscriptionAndLegacyFacadeKeepsRenderApi()
            throws ReflectiveOperationException {
        Method canonicalRenderer = GasAnalyserGUIOverlay.class
                .getMethod("render", RenderGuiEvent.Pre.class);
        Method legacyRenderer = net.mcreator.ssc.client.screens.GasAnalyserGUIOverlay.class
                .getMethod("render", RenderGuiEvent.Pre.class);

        assertNotNull(GasAnalyserGUIOverlay.class.getAnnotation(EventBusSubscriber.class));
        assertNotNull(canonicalRenderer.getAnnotation(SubscribeEvent.class));
        assertTrue(Modifier.isStatic(legacyRenderer.getModifiers()));
    }
}
