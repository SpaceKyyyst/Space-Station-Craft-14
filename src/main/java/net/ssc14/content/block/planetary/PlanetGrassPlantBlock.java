package net.ssc14.content.block.planetary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.content.block.Ssc14Blocks;

/** Shared behavior for the low and medium planetary grass variants. */
public class PlanetGrassPlantBlock extends Block {
    private static final VoxelShape SHAPE = box(4, 0, 4, 12, 12, 12);

    public PlanetGrassPlantBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.GRASS).strength(0.1F, 1.0F).noCollision()
                .isRedstoneConductor((state, level, pos) -> false)
                .replaceable().offsetType(OffsetType.XZ));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

    @Override
    public VoxelShape getVisualShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (level instanceof LevelAccessor) {
            return level.getBlockState(pos.below()).is(Ssc14Blocks.GRASS_LIGHT_BLOCK.get());
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos currentPos,
            Direction facing,
            BlockPos facingPos,
            BlockState facingState,
            RandomSource random) {
        return !state.canSurvive(level, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, scheduledTickAccess, currentPos, facing, facingPos, facingState, random);
    }
}
