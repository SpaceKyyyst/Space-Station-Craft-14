package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.ssc14.content.item.tool.GasAnalyserItem;
import org.junit.jupiter.api.Test;

class Ssc14GasAnalyserItemCompatibilityTest {
    @Test
    void canonicalRegistryOwnsIdAndLegacyHolderRemainsAlias() {
        assertSame(Ssc14Items.GAS_ANALYSER, Ssc14ModItems.GAS_ANALYSER);
        assertEquals("ssc_14:gas_analyser", Ssc14Items.GAS_ANALYSER.getId().toString());
    }

    @Test
    void legacyItemRemainsConstructibleFacade() {
        assertTrue(GasAnalyserItem.class.isAssignableFrom(
                net.mcreator.ssc.item.GasAnalyserItem.class));
        assertDoesNotThrow(() -> net.mcreator.ssc.item.GasAnalyserItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
    }
}
