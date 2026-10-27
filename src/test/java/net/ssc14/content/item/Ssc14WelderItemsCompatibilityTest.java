package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.item.ActiveWelderItem;
import net.mcreator.ssc.item.WeldingItem;
import net.ssc14.content.item.tool.WelderItem;
import net.ssc14.content.item.tool.WelderState;
import org.junit.jupiter.api.Test;

class Ssc14WelderItemsCompatibilityTest {
    @Test
    void canonicalRegistryOwnsBothIdsAndLegacyHoldersRemainAliases() {
        assertSame(Ssc14Items.WELDING, Ssc14ModItems.WELDING);
        assertSame(Ssc14Items.ACTIVE_WELDER, Ssc14ModItems.ACTIVE_WELDER);
        assertEquals("ssc_14:welding", Ssc14Items.WELDING.getId().toString());
        assertEquals("ssc_14:active_welder", Ssc14Items.ACTIVE_WELDER.getId().toString());
    }

    @Test
    void canonicalStatesAndLegacyFacadesRemainExplicit() {
        assertTrue(WelderItem.class.isAssignableFrom(WeldingItem.class));
        assertTrue(WelderItem.class.isAssignableFrom(ActiveWelderItem.class));
        assertEquals(2, WelderState.values().length);
        assertDoesNotThrow(() -> WeldingItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
        assertDoesNotThrow(() -> ActiveWelderItem.class
                .getConstructor(net.minecraft.world.item.Item.Properties.class));
    }
}
