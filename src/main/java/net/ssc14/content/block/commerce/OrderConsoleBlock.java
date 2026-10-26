package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical order console block. */
public class OrderConsoleBlock extends CommerceConsoleBlock {
    public OrderConsoleBlock(BlockBehaviour.Properties properties) {
        super(properties, CommerceConsoleIntegrationService.ConsoleKind.ORDER);
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OrderConsoleBlockEntity(pos, state);
    }
}
