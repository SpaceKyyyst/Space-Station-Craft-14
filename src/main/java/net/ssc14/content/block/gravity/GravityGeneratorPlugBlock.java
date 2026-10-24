package net.ssc14.content.block.gravity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Invisible structural proxy that follows the lifecycle of a gravity generator. */
public class GravityGeneratorPlugBlock extends Block {
    public GravityGeneratorPlugBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.EMPTY).strength(-1.0F, 3_600_000.0F)
                .noOcclusion().isRedstoneConductor((state, level, pos) -> false));
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
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor,
            @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighbor, orientation, movedByPiston);
        GravityGeneratorPlugLifecycleService.neighborChanged(level, pos);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
            ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean destroyed = super.onDestroyedByPlayer(
                state, level, pos, player, tool, willHarvest, fluid);
        GravityGeneratorPlugLifecycleService.destroyed(level, pos);
        return destroyed;
    }
}
