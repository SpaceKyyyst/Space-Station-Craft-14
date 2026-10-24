package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** @deprecated Use {@link net.ssc14.content.block.machinery.PortableSheaterBlock}. */
@Deprecated(forRemoval = false)
public class PortableSheaterBlock
        extends net.ssc14.content.block.machinery.PortableSheaterBlock {
    public PortableSheaterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new net.mcreator.ssc.block.entity.PortableSheaterBlockEntity(pos, state);
    }
}
