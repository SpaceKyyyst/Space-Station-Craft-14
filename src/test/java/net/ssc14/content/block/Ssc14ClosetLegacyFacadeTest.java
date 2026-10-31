package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.Test;

class Ssc14ClosetLegacyFacadeTest {
    @Test
    void generatedClosetEntitiesDelegateToCanonicalInventoryImplementation()
            throws ReflectiveOperationException {
        String[] legacyNames = {
                "ClosetBlockEntity", "ClosetEmergBlockEntity", "ClosetFireBlockEntity",
                "ClosetBlackBlockEntity", "ClosetBlueBlockEntity", "ClosetGrayBlockEntity",
                "ClosetGreenBlockEntity", "ClosetMixedBlockEntity", "ClosetN2BlockEntity",
                "ClosetOrangeBlockEntity", "ClosetPinkBlockEntity", "ClosetSecureBlockEntity"
        };

        for (String legacyName : legacyNames) {
            Class<?> legacyType = Class.forName("net.mcreator.ssc.block.entity." + legacyName);
            Constructor<?> constructor = legacyType.getConstructor(BlockPos.class, BlockState.class);

            assertEquals(net.ssc14.content.block.storage.ClosetBlockEntity.class,
                    legacyType.getSuperclass());
            assertTrue(constructor.trySetAccessible());
        }
    }
}
