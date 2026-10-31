package net.ssc14.architecture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LegacyRenderFacadesRemovalTest {
    @Test
    void generatedRenderFacadesAreRemoved() {
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModCuriosRenderers"));
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModEntityRenderers"));
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModHumanoidModels"));
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModModels"));
        assertThrows(ClassNotFoundException.class,
                () -> Class.forName("net.mcreator.ssc.init.Ssc14ModRenderStateModifiers"));
    }
}
