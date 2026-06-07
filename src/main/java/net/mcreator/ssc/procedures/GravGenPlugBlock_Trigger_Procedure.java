package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class GravGenPlugBlock_Trigger_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get() || (world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get() || (world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
				|| (world.getBlockState(BlockPos.containing(x, y - 1, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get() || (world.getBlockState(BlockPos.containing(x, y - 1, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
				|| (world.getBlockState(BlockPos.containing(x + 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
				|| (world.getBlockState(BlockPos.containing(x - 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get())) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		} else {
			Ssc14Mod.queueServerWork(1, () -> {
				if (!((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get() || (world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
						|| (world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get() || (world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
						|| (world.getBlockState(BlockPos.containing(x, y - 1, z + 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
						|| (world.getBlockState(BlockPos.containing(x, y - 1, z - 1))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
						|| (world.getBlockState(BlockPos.containing(x + 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get()
						|| (world.getBlockState(BlockPos.containing(x - 1, y - 1, z))).getBlock() == Ssc14ModBlocks.GRAVITY_GENERATOR.get())) {
					world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
				}
			});
		}
	}
}