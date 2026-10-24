package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.ssc14.content.block.machinery.AssembledMachineFrameBlock;
import net.ssc14.content.block.machinery.MachineFrameBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksMachineFrameCompatibilityTest {
    @Test
    void canonicalRegistryOwnsMachineFrameIdsAndLegacyFacadeAliasesHolders() {
        assertEquals("ssc_14:machine_frame", Ssc14Blocks.MACHINE_FRAME.getId().toString());
        assertEquals("ssc_14:machine_frame_2", Ssc14Blocks.MACHINE_FRAME_2.getId().toString());
        assertSame(Ssc14Blocks.MACHINE_FRAME, Ssc14ModBlocks.MACHINE_FRAME);
        assertSame(Ssc14Blocks.MACHINE_FRAME_2, Ssc14ModBlocks.MACHINE_FRAME_2);
    }

    @Test
    void legacyMachineFramesRemainConstructorCompatibleWrappers() throws Exception {
        assertWrapper("net.mcreator.ssc.block.MachineFrameBlock", MachineFrameBlock.class);
        assertWrapper("net.mcreator.ssc.block.MachineFrame2Block", AssembledMachineFrameBlock.class);
    }

    @Test
    void canonicalMachineFramesKeepLegacyStateDefaults() {
        MachineFrameBlock frame = (MachineFrameBlock) Ssc14Blocks.MACHINE_FRAME.get();
        AssembledMachineFrameBlock assembled =
                (AssembledMachineFrameBlock) Ssc14Blocks.MACHINE_FRAME_2.get();

        assertEquals(Direction.NORTH,
                frame.defaultBlockState().getValue(MachineFrameBlock.FACING));
        assertEquals(Direction.NORTH,
                assembled.defaultBlockState().getValue(AssembledMachineFrameBlock.FACING));
        assertFalse(assembled.defaultBlockState().getValue(AssembledMachineFrameBlock.READY));
        assertEquals(10.0F, frame.defaultDestroyTime());
        assertEquals(10.0F, assembled.defaultDestroyTime());
    }

    private static void assertWrapper(String className, Class<? extends Block> canonical)
            throws Exception {
        Class<?> legacy = Class.forName(className);
        assertTrue(canonical.isAssignableFrom(legacy), className);
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
