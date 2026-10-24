package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.ssc.block.entity.NuclearWarheadBlockEntity;

/** @deprecated Use {@link net.ssc14.content.block.explosives.NuclearWarheadBlock}. */
@Deprecated(forRemoval = false)
public class NuclearWarheadBlock
        extends net.ssc14.content.block.explosives.NuclearWarheadBlock {
    public NuclearWarheadBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NuclearWarheadBlockEntity(pos, state);
    }
}
