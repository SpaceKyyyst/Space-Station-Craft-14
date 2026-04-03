package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class BaseAirlockU1_ChekProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1.get() || (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Ssc14ModBlocks.BASE_AIRLOCK_D_1OPEN.get())) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}
}