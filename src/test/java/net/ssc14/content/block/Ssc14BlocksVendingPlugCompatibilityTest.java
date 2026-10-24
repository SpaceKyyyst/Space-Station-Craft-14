package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.commerce.VendingUpperPlugBlock;
import org.junit.jupiter.api.Test;

class Ssc14BlocksVendingPlugCompatibilityTest {
    @Test
    void canonicalRegistryOwnsVendingPlugIdAndLegacyFacadeAliasesHolder() {
        assertEquals("ssc_14:vending_kostil", Ssc14Blocks.VENDING_KOSTIL.getId().toString());
        assertSame(Ssc14Blocks.VENDING_KOSTIL, Ssc14ModBlocks.VENDING_KOSTIL);
    }

    @Test
    void legacyVendingPlugTypeRemainsAConstructorCompatibleWrapper() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.VendingKOSTILBlock");
        assertTrue(VendingUpperPlugBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
    }

    @Test
    void canonicalVendingPlugKeepsStrengthAndLightingContract() {
        VendingUpperPlugBlock block =
                (VendingUpperPlugBlock) Ssc14Blocks.VENDING_KOSTIL.get();
        assertEquals(20.0F, block.defaultDestroyTime());
        assertEquals(10.0F, block.getExplosionResistance());
        assertTrue(block.defaultBlockState().getVisualShape(
                net.minecraft.world.level.EmptyBlockGetter.INSTANCE,
                net.minecraft.core.BlockPos.ZERO,
                net.minecraft.world.phys.shapes.CollisionContext.empty()).isEmpty());
    }
}
