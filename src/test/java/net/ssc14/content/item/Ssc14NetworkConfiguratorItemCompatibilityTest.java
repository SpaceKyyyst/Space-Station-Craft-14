package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ssc14.content.item.tool.NetworkConfiguratorItem;
import org.junit.jupiter.api.Test;

class Ssc14NetworkConfiguratorItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:network_configurator",
                Ssc14Items.NETWORK_CONFIGURATOR.getId().toString());
    }

    @Test
    void legacyItemRemainsFacade() {
        assertTrue(NetworkConfiguratorItem.class.isAssignableFrom(
                net.mcreator.ssc.item.NetworkConfiguratorItem.class));
    }
}
