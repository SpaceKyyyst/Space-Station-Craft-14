package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.storage.UpperClosetPlugBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksUpperClosetPlugCompatibilityTest {
    @Test
    void canonicalRegistryOwnsUpperPlugIdAndPreservesCanonicalId() {
        assertEquals("ssc_14:up_closet_plug",
                Ssc14Blocks.UP_CLOSET_PLUG.getId().toString());
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
