package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class LowPlanetGrass_InstallationProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (Ssc14ModBlocks.GRASS_LIGHT_BLOCK.get() == (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock()) {
			return true;
		}
		return false;
	}
}