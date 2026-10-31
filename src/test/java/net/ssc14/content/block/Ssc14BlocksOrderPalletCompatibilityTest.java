package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.commerce.OrderPalletBlock;
import net.ssc14.content.block.commerce.OrderPalletBlockEntity;
import net.ssc14.content.block.commerce.Ssc14CommerceBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksOrderPalletCompatibilityTest {
    @Test
    void canonicalRegistryOwnsOrderPalletIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:order_pallet", Ssc14Blocks.ORDER_PALLET.getId().toString());
    }

    @Test
    void legacyOrderPalletTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacy = Class.forName("net.mcreator.ssc.block.OrderPalletBlock");
        assertTrue(OrderPalletBlock.class.isAssignableFrom(legacy));
        legacy.getConstructor(BlockBehaviour.Properties.class);
        assertTrue(OrderPalletBlockEntity.class.isAssignableFrom(Class.forName(
                "net.mcreator.ssc.block.entity.OrderPalletBlockEntity")));
    }

    @Test
    void canonicalOrderPalletKeepsPhysicalDefaults() {
        OrderPalletBlock pallet = (OrderPalletBlock) Ssc14Blocks.ORDER_PALLET.get();
        assertEquals(30.0F, pallet.defaultDestroyTime());
        assertEquals(0, pallet.getLightDampening(pallet.defaultBlockState()));
    }
}
