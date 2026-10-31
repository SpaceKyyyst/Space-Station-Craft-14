package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyReagentAliasRemovalTest {
    @Test
    void generatedReagentAliasIsRemoved() {
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.ModReagents"));
    }
}
