package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class VendingAut_UPDATE_Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!(Ssc14ModBlocks.VENDING_KOSTIL.get() == (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock())) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
		}
	}
}