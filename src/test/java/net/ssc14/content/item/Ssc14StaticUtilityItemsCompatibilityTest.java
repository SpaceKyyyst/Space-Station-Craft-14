package net.ssc14.content.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14StaticUtilityItemsCompatibilityTest {
    private static final List<ExpectedItem> ITEMS = List.of(
            item("RC_DCARTRIDGE", "rc_dcartridge", "RCDcartridgeItem",
                    "net.ssc14.content.item.utility.RcdCartridgeItem"),
            item("CONE", "cone", "ConeItem",
                    "net.ssc14.content.item.utility.TrafficConeItem"),
            item("DEBUGACTIVATOR", "debugactivator", "DEBUGACTIVATORItem",
                    "net.ssc14.content.item.utility.DebugActivatorItem"),
            item("DEBU_GITEM", "debu_gitem", "DEBUGitemItem",
                    "net.ssc14.content.item.utility.DebugBroadcastItem"));

    @Test
    void canonicalRegistryOwnsStaticUtilityIdsAndPreservesCanonicalIds() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Field canonicalField = Ssc14Items.class.getField(expected.fieldName());
            Object canonicalHolder = canonicalField.get(null);
            assertEquals("ssc_14:" + expected.id(),
                    ((DeferredHolder<?, ?>) canonicalHolder).getId().toString(),
                    expected.fieldName());
        }
    }

    @Test
    void legacyTypesDelegateToTheirCanonicalRoles() throws Exception {
        for (ExpectedItem expected : ITEMS) {
            Class<?> canonicalType = Class.forName(expected.canonicalClassName());
            Class<?> legacyType = Class.forName(
                    "net.mcreator.ssc.item." + expected.legacyClassName());
            assertTrue(canonicalType.isAssignableFrom(legacyType), expected.legacyClassName());
        }
    }

    private static ExpectedItem item(
            String fieldName, String id, String legacyClassName, String canonicalClassName) {
        return new ExpectedItem(fieldName, id, legacyClassName, canonicalClassName);
    }

    private record ExpectedItem(
            String fieldName, String id, String legacyClassName, String canonicalClassName) { }
}
