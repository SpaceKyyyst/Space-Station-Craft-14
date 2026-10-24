package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.debug.FailureSentinelBlock}. */
@Deprecated(forRemoval = false)
public class KostilniblokBlock extends net.ssc14.content.block.debug.FailureSentinelBlock {
    public KostilniblokBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new net.mcreator.ssc.block.entity.KostilniblokBlockEntity(pos, state);
    }
}
