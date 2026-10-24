package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.airlock.AirlockThresholdBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksAirlockThresholdCompatibilityTest {
    @Test
    void canonicalRegistryOwnsThresholdIdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:airlock_threshold",
                Ssc14Blocks.AIRLOCK_THRESHOLD.getId().toString());
        assertSame(Ssc14Blocks.AIRLOCK_THRESHOLD, Ssc14ModBlocks.AIRLOCK_THRESHOLD);
    }

    @Test
    void legacyThresholdTypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.AirlockThresholdBlock");
        assertTrue(AirlockThresholdBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalThresholdKeepsInvisibleUnbreakableContract() {
        AirlockThresholdBlock block =
                (AirlockThresholdBlock) Ssc14Blocks.AIRLOCK_THRESHOLD.get();
        assertEquals(RenderShape.INVISIBLE, block.getRenderShape(block.defaultBlockState()));
        assertEquals(-1.0F, block.defaultDestroyTime());
    }
}
