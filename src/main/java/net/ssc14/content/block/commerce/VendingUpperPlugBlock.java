package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Canonical non-item upper interaction plug for two-block vending machines. */
public class VendingUpperPlugBlock extends Block {
    private static final TagKey<Block> VENDING_MACHINES = BlockTags.create(
            Identifier.fromNamespaceAndPath("ssc14", "vendings"));
    private static final VoxelShape SHAPE = box(4, 0, 4, 12, 14, 12);

    public VendingUpperPlugBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.EMPTY)
                .strength(20.0F, 10.0F)
                .noOcclusion()
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
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state,
            boolean includeData, Player player) {
        return ItemStack.EMPTY;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        validateSupport(level, pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock,
            Orientation orientation, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, orientation, moving);
        validateSupport(level, pos);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        BlockPos lowerPos = pos.below();
        BlockHitResult lowerHit = BlockHitResult.miss(
                new Vec3(lowerPos.getX(), lowerPos.getY(), lowerPos.getZ()),
                Direction.UP, lowerPos);
        level.getBlockState(lowerPos).useWithoutItem(level, player, lowerHit);
        return InteractionResult.SUCCESS;
    }

    private static void validateSupport(Level level, BlockPos pos) {
        if (!level.getBlockState(pos.below()).is(VENDING_MACHINES)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }
}
