package net.ssc14.core.network;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MenuStateRulesTest {
    @Test
    void acceptsOnlyDeclaredAccessFieldsWithBooleanPayloads() {
        assertTrue(MenuStateRules.acceptsClientField("AccessConfigMENUMenu", 1, "Atmos"));
        assertFalse(MenuStateRules.acceptsClientField("AccessConfigMENUMenu", 0, "Atmos"));
        assertFalse(MenuStateRules.acceptsClientField("AccessConfigMENUMenu", 1, "unknown"));
    }

    @Test
    void preservesIdCardJobNameAndAccessFieldContracts() {
        assertTrue(MenuStateRules.acceptsClientField("IDcodeMenu", 0, "Job_Name"));
        assertTrue(MenuStateRules.acceptsClientField("IDcodeMenu", 1, "Job_Name_Readact"));
        assertTrue(MenuStateRules.acceptsClientField("IDcodeMenu", 1, "Capitan"));
        assertFalse(MenuStateRules.acceptsClientField("IDcodeMenu", 0, "Job_Name_Readact"));
        assertFalse(MenuStateRules.acceptsClientField("IDcodeMenu", 1, "Job_Name"));
    }

    @Test
    void rejectsUnknownMenusAndUnsupportedTypes() {
        assertFalse(MenuStateRules.acceptsClientField("UnknownMenu", 1, "Atmos"));
        assertFalse(MenuStateRules.acceptsClientField("DECALScolorGUIMenu", 1, "R"));
        assertTrue(MenuStateRules.acceptsClientField("DECALScolorGUIMenu", 0, "R"));
        assertFalse(MenuStateRules.acceptsClientField("DECALScolorGUIMenu", 0, "Alpha"));
    }
}
