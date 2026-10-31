package net.ssc14.client.overlay;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.junit.jupiter.api.Test;

class WorldCheckOverlayBoundaryTest {
    @Test
    void canonicalOverlayOwnsClientSubscriptionAndLegacyFacadeKeepsHandlerApi()
            throws ReflectiveOperationException {
        Method canonicalHandler = WorldCheckUIOverlay.class
                .getMethod("eventHandler", RenderGuiEvent.Pre.class);
        Method legacyHandler = net.mcreator.ssc.client.screens.WorldCheckUIOverlay.class
                .getMethod("eventHandler", RenderGuiEvent.Pre.class);

        assertNotNull(WorldCheckUIOverlay.class.getAnnotation(EventBusSubscriber.class));
        assertNotNull(canonicalHandler.getAnnotation(SubscribeEvent.class));
        assertTrue(Modifier.isStatic(legacyHandler.getModifiers()));
    }
}
