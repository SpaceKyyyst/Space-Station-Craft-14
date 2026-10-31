package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Ssc14AirlockAccessFacadeTest {
    @Test
    void generatedAirlockAccessTypesAreExplicitCompatibilityFacades()
            throws ReflectiveOperationException {
        String[] legacyNames = {
                "AirlockAccessPolicy", "AirlockAiControl", "AirlockConfigurator",
                "AirlockDisassembly", "AirlockEventLog", "AirlockMaintenance",
                "AirlockManualOpening", "AirlockPower", "AirlockSignals",
                "AirlockWelding", "AirlockWirePulses", "AirlockWireService"
        };

        for (String legacyName : legacyNames) {
            Class<?> legacyType = Class.forName("net.mcreator.ssc.access." + legacyName);
            assertTrue(legacyType.isAnnotationPresent(Deprecated.class), legacyName);
        }
    }
}
