package net.ssc14.content.block.window;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared transparent rendering contract for window blocks. */
public abstract class AbstractWindowBlock extends Block {
    protected AbstractWindowBlock(BlockBehaviour.Properties properties, float strength, float resistance) {
        super(properties.sound(SoundType.GLASS).strength(strength, resistance)
                .noOcclusion().isRedstoneConductor((state, getter, pos) -> false));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction side) {
        return adjacentState.getBlock() == this || super.skipRendering(state, adjacentState, side);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter getter, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }
}
