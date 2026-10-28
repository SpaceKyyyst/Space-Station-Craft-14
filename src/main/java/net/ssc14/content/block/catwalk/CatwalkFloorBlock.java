package net.ssc14.content.block.catwalk;

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

public class CatwalkFloorBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.or(
            box(0, 12, 0, 16, 16, 16),
            box(0, 0, 0, 1, 16, 1),
            box(0, 0, 15, 1, 16, 16),
            box(15, 0, 0, 16, 16, 1),
            box(15, 0, 15, 16, 16, 16));

    public CatwalkFloorBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.CHAIN).strength(50.0f, 2.5f).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level instanceof LevelAccessor accessor
                ? CatwalkSupportPolicy.canSupport(accessor, pos)
                : super.canSurvive(state, level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level,
            ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction direction,
            BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return state.canSurvive(level, currentPos)
                ? super.updateShape(state, level, scheduledTickAccess, currentPos, direction,
                        neighborPos, neighborState, random)
                : Blocks.AIR.defaultBlockState();
    }
}
