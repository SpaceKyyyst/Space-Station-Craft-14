package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.explosives.C4ChargeBlock;
import net.ssc14.content.block.explosives.C4ChargeBlockEntity;
import net.ssc14.content.block.explosives.Ssc14ExplosiveBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksC4CompatibilityTest {
    @Test
    void canonicalRegistriesOwnC4IdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:c_4", Ssc14Blocks.C_4.getId().toString());
        assertEquals("ssc_14:c_4", Ssc14ExplosiveBlockEntities.C_4.getId().toString());
        assertSame(Ssc14Blocks.C_4, Ssc14ModBlocks.C_4);
    }

    @Test
    void legacyC4TypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.C4Block");
        assertTrue(C4ChargeBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName("net.mcreator.ssc.block.entity.C4BlockEntity");
        assertTrue(C4ChargeBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalC4KeepsLegacyPhysicalDefaults() {
        C4ChargeBlock block = (C4ChargeBlock) Ssc14Blocks.C_4.get();
        assertEquals(Direction.NORTH, block.defaultBlockState().getValue(C4ChargeBlock.FACING));
        assertEquals(50.0F, block.defaultDestroyTime());
        assertEquals(5.0F, block.getExplosionResistance());
        assertEquals(1, block.defaultBlockState().getLightEmission());
    }

}
