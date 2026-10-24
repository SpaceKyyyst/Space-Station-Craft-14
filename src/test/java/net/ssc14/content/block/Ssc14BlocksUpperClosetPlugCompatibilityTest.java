package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.storage.UpperClosetPlugBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksUpperClosetPlugCompatibilityTest {
    @Test
    void canonicalRegistryOwnsUpperPlugIdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:up_closet_plug",
                Ssc14Blocks.UP_CLOSET_PLUG.getId().toString());
        assertSame(Ssc14Blocks.UP_CLOSET_PLUG, Ssc14ModBlocks.UP_CLOSET_PLUG);
    }

    @Test
    void legacyUpperPlugTypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.UpClosetPLUGBlock");
        assertTrue(UpperClosetPlugBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalUpperPlugKeepsDirectionalUnbreakableContract() {
        UpperClosetPlugBlock block =
                (UpperClosetPlugBlock) Ssc14Blocks.UP_CLOSET_PLUG.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(UpperClosetPlugBlock.FACING));
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(0, block.getLightDampening(block.defaultBlockState()));
    }
}
