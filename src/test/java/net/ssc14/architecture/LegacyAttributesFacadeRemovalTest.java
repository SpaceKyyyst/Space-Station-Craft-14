package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyAttributesFacadeRemovalTest {
    @Test
    void generatedAttributesFacadeIsRemoved() {
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModAttributes"));
    }
}
