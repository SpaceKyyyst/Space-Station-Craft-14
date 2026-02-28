package net.mcreator.ssc.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class CaveFloor2Block extends Block {
	public CaveFloor2Block(BlockBehaviour.Properties properties) {
		super(properties.strength(-1, 3600000));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData, Player entity) {
		return new ItemStack(Ssc14ModBlocks.CAVE_FLOOR_1.get());
	}
}