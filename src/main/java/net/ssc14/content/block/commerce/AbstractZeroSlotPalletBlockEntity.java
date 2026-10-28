package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.ZeroSlotContainerBlockEntity;

/** Shared zero-slot menu and capability contract for cargo pallets. */
public abstract class AbstractZeroSlotPalletBlockEntity
        extends ZeroSlotContainerBlockEntity {
    public static final int SLOT_COUNT = ZeroSlotContainerBlockEntity.SLOT_COUNT;

    protected AbstractZeroSlotPalletBlockEntity(BlockEntityType<?> type, BlockPos pos,
            BlockState state, String defaultName, String displayName) {
        super(type, pos, state, defaultName, displayName);
    }
}
