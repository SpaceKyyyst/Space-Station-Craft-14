package net.ssc14.content.block.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.Ssc14Blocks;

abstract class CaveFloorVariantBlock extends CaveFloorBlock {
    CaveFloorVariantBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getCloneItemStack(
            LevelReader world, BlockPos pos, BlockState state, boolean includeData, Player player) {
        return new ItemStack(Ssc14Blocks.CAVE_FLOOR_1.get());
    }
}
