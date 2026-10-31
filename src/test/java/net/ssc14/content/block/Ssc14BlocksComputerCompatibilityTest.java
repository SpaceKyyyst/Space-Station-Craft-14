package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.machinery.ComputerBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksComputerCompatibilityTest {
    @Test
    void canonicalRegistryOwnsComputerIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:computer", Ssc14Blocks.COMPUTER.getId().toString());
    }

    @Test
    void legacyComputerTypeRemainsConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.ComputerBlock");
        assertTrue(ComputerBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalComputerKeepsLegacyStateAndPhysicalDefaults() {
        ComputerBlock block = (ComputerBlock) Ssc14Blocks.COMPUTER.get();
        assertEquals(Direction.NORTH, block.defaultBlockState().getValue(ComputerBlock.FACING));
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
    }
}
