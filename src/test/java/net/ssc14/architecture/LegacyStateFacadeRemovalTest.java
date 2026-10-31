package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyStateFacadeRemovalTest {
    @Test
    void generatedSavedDataFacadeIsRemoved() {
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.network.Ssc14ModVariables"));
    }
}
