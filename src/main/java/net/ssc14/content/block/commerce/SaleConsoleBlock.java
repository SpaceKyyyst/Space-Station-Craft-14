package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical sale console block. */
public class SaleConsoleBlock extends CommerceConsoleBlock {
    public SaleConsoleBlock(BlockBehaviour.Properties properties) {
        super(properties, CommerceConsoleIntegrationService.ConsoleKind.SALE);
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SaleConsoleBlockEntity(pos, state);
    }
}
