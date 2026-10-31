package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.Test;

class Ssc14LightingLegacyFacadeTest {
    @Test
    void generatedLightingEntitiesDelegateToCanonicalImplementation()
            throws ReflectiveOperationException {
        assertFacade("LampBlockEntity");
        assertFacade("EmergencyLightBlockEntity");
    }

    private static void assertFacade(String legacyName) throws ReflectiveOperationException {
        Class<?> legacyType = Class.forName("net.mcreator.ssc.block.entity." + legacyName);
        Constructor<?> constructor = legacyType.getConstructor(BlockPos.class, BlockState.class);

        assertEquals(net.ssc14.content.block.lighting.LightingBlockEntity.class,
                legacyType.getSuperclass());
        assertTrue(constructor.trySetAccessible());
    }
}
