package net.ssc14.content.block.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CaveFloor1Block extends CaveFloorBlock {
    public CaveFloor1Block(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        CaveFloorRandomizer.replace(world, pos);
    }
}
