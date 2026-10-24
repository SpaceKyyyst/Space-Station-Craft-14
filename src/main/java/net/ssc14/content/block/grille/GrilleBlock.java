package net.ssc14.content.block.grille;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrilleBlock extends Block {
    public GrilleBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.CHAIN).strength(5.0f).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
            ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean destroyed = super.onDestroyedByPlayer(state, level, pos, player, tool, willHarvest, fluid);
        GrilleBreakService.replaceWithBroken(level, pos, player);
        return destroyed;
    }
}
