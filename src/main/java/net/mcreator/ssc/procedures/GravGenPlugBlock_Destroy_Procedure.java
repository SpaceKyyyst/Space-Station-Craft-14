package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class GravGenPlugBlock_Destroy_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x + 1, y, z), false);
		} else if ((world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x - 1, y, z), false);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x, y, z + 1), false);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x, y, z - 1), false);
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x, y - 1, z + 1), false);
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x, y - 1, z - 1), false);
		} else if ((world.getBlockState(BlockPos.containing(x + 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x + 1, y - 1, z), false);
		} else if ((world.getBlockState(BlockPos.containing(x - 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()) {
			world.destroyBlock(BlockPos.containing(x - 1, y - 1, z), false);
		}
	}
}