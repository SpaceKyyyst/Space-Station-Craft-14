package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.machinery.PortableSheaterBlock;
import net.ssc14.content.block.machinery.PortableSheaterBlockEntity;
import net.ssc14.content.block.machinery.Ssc14MachineBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksPortableSheaterCompatibilityTest {
    @Test
    void canonicalRegistriesOwnPortableSheaterIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:portable_sheater",
                Ssc14Blocks.PORTABLE_SHEATER.getId().toString());
        assertEquals("ssc_14:portable_sheater",
                Ssc14MachineBlockEntities.PORTABLE_SHEATER.getId().toString());
        assertSame(Ssc14Blocks.PORTABLE_SHEATER, Ssc14ModBlocks.PORTABLE_SHEATER);
        assertSame(Ssc14MachineBlockEntities.PORTABLE_SHEATER,
                Ssc14MachineBlockEntities.PORTABLE_SHEATER);
    }

    @Test
    void legacyPortableSheaterTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.PortableSheaterBlock");
        assertTrue(PortableSheaterBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);

        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.PortableSheaterBlockEntity");
        assertTrue(PortableSheaterBlockEntity.class.isAssignableFrom(legacyEntity));
        legacyEntity.getConstructor(net.minecraft.core.BlockPos.class,
                net.minecraft.world.level.block.state.BlockState.class);
    }

    @Test
    void canonicalPortableSheaterKeepsLegacyStateAndPhysicalDefaults() {
        PortableSheaterBlock block =
                (PortableSheaterBlock) Ssc14Blocks.PORTABLE_SHEATER.get();
        assertEquals(Direction.NORTH,
                block.defaultBlockState().getValue(PortableSheaterBlock.FACING));
        assertEquals(0,
                block.defaultBlockState().getValue(PortableSheaterBlock.BLOCKSTATE));
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
    }
}
