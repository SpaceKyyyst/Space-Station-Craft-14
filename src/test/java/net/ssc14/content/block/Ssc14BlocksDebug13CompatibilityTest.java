package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.debug.StationCodeDebugBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksDebug13CompatibilityTest {
    @Test
    void canonicalRegistryOwnsDebug13IdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:debug_13", Ssc14Blocks.DEBUG_13.getId().toString());
        assertSame(Ssc14Blocks.DEBUG_13, Ssc14ModBlocks.DEBUG_13);
    }

    @Test
    void legacyDebug13TypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.DEBUG13Block");
        assertTrue(StationCodeDebugBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalDebug13KeepsStateRangeShapeAndPhysicalDefaults() {
        StationCodeDebugBlock block = (StationCodeDebugBlock) Ssc14Blocks.DEBUG_13.get();
        assertEquals(0, block.defaultBlockState().getValue(StationCodeDebugBlock.BLOCKSTATE));
        assertEquals(3, StationCodeDebugBlock.BLOCKSTATE.getPossibleValues().size());
        assertTrue(StationCodeDebugBlock.BLOCKSTATE.getPossibleValues().containsAll(
                java.util.List.of(0, 1, 2)));
        assertEquals(1.0F, block.defaultDestroyTime());
    }
}
