package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlockEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.commerce.SalePalletBlock;
import net.ssc14.content.block.commerce.SalePalletBlockEntity;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksSalePalletCompatibilityTest {
    @Test
    void canonicalRegistryOwnsSalePalletIdsAndLegacyFacadesAliasHolders() {
        assertEquals("ssc_14:sale_pallet", Ssc14Blocks.SALE_PALLET.getId().toString());
        assertSame(Ssc14Blocks.SALE_PALLET, Ssc14ModBlocks.SALE_PALLET);
        assertSame(Ssc14CommerceBlockEntities.SALE_PALLET, Ssc14ModBlockEntities.SALE_PALLET);
    }

    @Test
    void legacySalePalletTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.SalePalletBlock");
        assertTrue(SalePalletBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(SalePalletBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.SalePalletBlockEntity")));
    }

    @Test
    void canonicalSalePalletKeepsPhysicalDefaults() {
        SalePalletBlock pallet = (SalePalletBlock) Ssc14Blocks.SALE_PALLET.get();
        assertEquals(30.0F, pallet.defaultDestroyTime());
        assertEquals(0, pallet.getLightDampening(pallet.defaultBlockState()));
    }
}
