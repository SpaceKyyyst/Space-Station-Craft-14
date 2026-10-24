package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.storage.ExtinguisherCabinetBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksExtinguisherCabinetCompatibilityTest {
    @Test
    void canonicalRegistryOwnsCabinetIdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:extinguisher_cabinet",
                Ssc14Blocks.EXTINGUISHER_CABINET.getId().toString());
        assertSame(Ssc14Blocks.EXTINGUISHER_CABINET,
                Ssc14ModBlocks.EXTINGUISHER_CABINET);
    }

    @Test
    void legacyCabinetTypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.ExtinguisherCabinetBlock");
        assertTrue(ExtinguisherCabinetBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalCabinetKeepsStateAndStrengthContract() {
        ExtinguisherCabinetBlock block = (ExtinguisherCabinetBlock)
                Ssc14Blocks.EXTINGUISHER_CABINET.get();
        var state = block.defaultBlockState();
        assertEquals(Direction.NORTH, state.getValue(ExtinguisherCabinetBlock.FACING));
        assertFalse(state.getValue(ExtinguisherCabinetBlock.OPEN));
        assertFalse(state.getValue(ExtinguisherCabinetBlock.EMPTY));
        assertEquals(10.0F, block.defaultDestroyTime());
        assertEquals(5.0F, block.getExplosionResistance());
    }
}
