package net.ssc14.content.block.floor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.ssc14.content.sound.Ssc14SoundEvents;

/** Shared behavior for the generated simple station floor-tile variants. */
public abstract class AbstractFloorTileBlock extends Block {
    private final VoxelShape shape;

    protected AbstractFloorTileBlock(BlockBehaviour.Properties properties, VoxelShape shape) {
        super(properties.sound(new DeferredSoundType(1.0f, 1.0f,
                        Ssc14SoundEvents.TITLE_OFF,
                        () -> SoundEvents.COPPER_STEP,
                        Ssc14SoundEvents.TITLE_ON,
                        () -> SoundEvents.COPPER_HIT,
                        () -> SoundEvents.COPPER_FALL))
                .strength(100.0f, 5.0f)
                .noOcclusion()
                .dynamicShape()
                .isRedstoneConductor((state, level, pos) -> false));
        this.shape = shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return shape;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 15;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level instanceof LevelAccessor accessor
                ? FloorTileSupportPolicy.canSupport(accessor, pos)
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return FloorTileDoorwayCollision.collision(level, pos, shape);
    }
}
