package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Order pallet block entity. */
public class OrderPalletBlockEntity extends AbstractZeroSlotPalletBlockEntity {
    public OrderPalletBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14CommerceBlockEntities.ORDER_PALLET.get(), pos, state,
                "order_pallet", "Order Pallet");
    }
}
