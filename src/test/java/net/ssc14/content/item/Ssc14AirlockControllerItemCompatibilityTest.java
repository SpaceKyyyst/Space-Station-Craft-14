package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ssc14.content.item.tool.AirlockControllerItem;
import org.junit.jupiter.api.Test;

class Ssc14AirlockControllerItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:airlock_controller",
                Ssc14Items.AIRLOCK_CONTROLLER.getId().toString());
    }

    @Test
    void legacyItemRemainsConstructibleFacade() {
        assertTrue(AirlockControllerItem.class.isAssignableFrom(
                net.mcreator.ssc.item.AirlockControllerItem.class));
        assertDoesNotThrow(() -> net.mcreator.ssc.item.AirlockControllerItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
    }
}
