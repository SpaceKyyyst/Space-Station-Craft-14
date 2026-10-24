package net.ssc14.content.block.wall;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/** Shared player-destruction contract for full-cube station walls. */
public abstract class AbstractStationWallBlock extends Block {
    protected AbstractStationWallBlock(BlockBehaviour.Properties properties, float destroyTime,
            float resistance) {
        super(properties.sound(SoundType.NETHERITE_BLOCK).strength(destroyTime, resistance));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
            ItemStack tool, boolean willHarvest, FluidState fluid) {
        boolean removed = super.onDestroyedByPlayer(state, level, pos, player, tool,
                willHarvest, fluid);
        WallDamageService.breakByPlayer(level, pos.getX(), pos.getY(), pos.getZ(), player);
        return removed;
    }
}
