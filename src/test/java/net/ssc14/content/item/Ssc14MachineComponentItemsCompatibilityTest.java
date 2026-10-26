package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.item.machinery.MachineComponentItem;
import org.junit.jupiter.api.Test;

class Ssc14MachineComponentItemsCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.of(
            "CAPACITOR", "capacitor",
            "MATTER_BIN", "matter_bin",
            "MICRO_MANIPULATOR", "micro_manipulator",
            "SUBSTATION_BOARD", "substation_board",
            "SMES_BOARD", "smes_board",
            "APC_BOARD", "apc_board",
            "LOW_BATTERIE", "low_batterie");

    private static final Class<?>[] LEGACY_TYPES = {
            net.mcreator.ssc.item.CapacitorItem.class,
            net.mcreator.ssc.item.MatterBinItem.class,
            net.mcreator.ssc.item.MicroManipulatorItem.class,
            net.mcreator.ssc.item.SubstationBoardItem.class,
            net.mcreator.ssc.item.SMESBoardItem.class,
            net.mcreator.ssc.item.APCBoardItem.class,
            net.mcreator.ssc.item.LowBatterieItem.class
    };

    @Test
    void canonicalRegistryOwnsComponentIdsAndLegacyHoldersRemainAliases() throws Exception {
        for (Map.Entry<String, String> expected : EXPECTED_HOLDERS.entrySet()) {
            Field canonicalField = Ssc14Items.class.getField(expected.getKey());
            Field legacyField = Ssc14ModItems.class.getField(expected.getKey());
            Object canonicalHolder = canonicalField.get(null);
            assertSame(canonicalHolder, legacyField.get(null), expected.getKey());
            assertEquals("ssc_14:" + expected.getValue(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(), expected.getKey());
        }
    }

    @Test
    void legacyComponentTypesDelegateToCanonicalImplementation() throws Exception {
        for (Class<?> legacyType : LEGACY_TYPES) {
            assertTrue(MachineComponentItem.class.isAssignableFrom(legacyType), legacyType.getName());
            legacyType.getConstructor(Item.Properties.class);
        }
    }
}
