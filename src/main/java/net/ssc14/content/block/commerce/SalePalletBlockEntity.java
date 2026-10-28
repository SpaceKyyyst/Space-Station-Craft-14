package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** Sale pallet block entity. */
public class SalePalletBlockEntity extends AbstractZeroSlotPalletBlockEntity {
    public SalePalletBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14CommerceBlockEntities.SALE_PALLET.get(), pos, state,
                "sale_pallet", "Sale Pallet");
    }
}
