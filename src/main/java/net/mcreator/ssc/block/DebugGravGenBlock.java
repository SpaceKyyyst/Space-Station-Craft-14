package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.debug.DebugGravityGeneratorBlock}. */
@Deprecated(forRemoval = false)
public class DebugGravGenBlock
        extends net.ssc14.content.block.debug.DebugGravityGeneratorBlock {
    public DebugGravGenBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new net.mcreator.ssc.block.entity.DebugGravGenBlockEntity(pos, state);
    }
}
