package net.ssc14.legacytests.network.validation;

import org.junit.jupiter.api.Test;
import net.ssc14.core.network.MenuStateRules;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuStateRulesTest {
    @Test
    void onlyDeclaredWidgetsCanBeChangedByClient() {
        assertTrue(MenuStateRules.acceptsClientField("IDcodeMenu", 0, "Job_Name"));
        assertTrue(MenuStateRules.acceptsClientField("IDcodeMenu", 1, "HoS"));
        assertTrue(MenuStateRules.acceptsClientField("DECALScolorGUIMenu", 0, "R"));
        assertFalse(MenuStateRules.acceptsClientField("NetConfigGUIMenu", 0, "L1_id"));
        assertFalse(MenuStateRules.acceptsClientField("IDcodeMenu", 0, "HoS"));
        assertFalse(MenuStateRules.acceptsClientField("AccessConfigMENUMenu", 0, "Job_Name"));
        assertFalse(MenuStateRules.acceptsClientField("IDcodeMenu", 1, "arbitrary"));
    }

    @Test
    void rejectsUnknownTagsNullStatesAndNonfiniteNumbers() {
        assertThrows(IllegalArgumentException.class, () -> MenuStateRules.requireValid(3, "value", "text"));
        assertThrows(IllegalArgumentException.class, () -> MenuStateRules.requireValid(1, "value", null));
        assertThrows(IllegalArgumentException.class, () -> MenuStateRules.requireValid(2, "value", Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> MenuStateRules.requireValid(2, "value", Double.POSITIVE_INFINITY));
    }
}
