package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.energy.ApcBlock;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.ssc14.content.block.energy.PodstationBlock;
import net.ssc14.content.block.energy.PodstationBlockEntity;
import net.ssc14.content.block.energy.Ssc14EnergyMachineBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksEnergyMachineCompatibilityTest {
    @Test
    void canonicalRegistryOwnsEnergyMachineIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:apc", Ssc14Blocks.APC.getId().toString());
        assertEquals("ssc_14:podstation", Ssc14Blocks.PODSTATION.getId().toString());
        assertSame(Ssc14Blocks.APC, Ssc14ModBlocks.APC);
        assertSame(Ssc14Blocks.PODSTATION, Ssc14ModBlocks.PODSTATION);
    }

    @Test
    void legacyEnergyMachineTypesRemainConstructorCompatibleWrappers() throws Exception {
        assertWrapper("net.mcreator.ssc.block.APCBlock", ApcBlock.class);
        assertWrapper("net.mcreator.ssc.block.PodstationBlock", PodstationBlock.class);
        assertTrue(ApcBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.APCBlockEntity")));
        assertTrue(PodstationBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.PodstationBlockEntity")));
    }

    @Test
    void canonicalEnergyMachinesKeepDirectionalAndVisualDefaults() {
        ApcBlock apc = (ApcBlock) Ssc14Blocks.APC.get();
        PodstationBlock podstation = (PodstationBlock) Ssc14Blocks.PODSTATION.get();
        assertEquals(Direction.NORTH, apc.defaultBlockState().getValue(ApcBlock.FACING));
        assertEquals(0, apc.defaultBlockState().getValue(ApcBlock.BLOCKSTATE));
        assertEquals(Direction.NORTH,
                podstation.defaultBlockState().getValue(PodstationBlock.FACING));
        assertEquals(30.0F, apc.defaultDestroyTime());
        assertEquals(50.0F, podstation.defaultDestroyTime());
    }

    private static void assertWrapper(String className, Class<? extends Block> canonical)
            throws Exception {
        Class<?> legacy = Class.forName(className);
        assertTrue(canonical.isAssignableFrom(legacy), className);
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }
}
