package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.ssc14.content.block.Ssc14Blocks;

/** @deprecated Compatibility adapter for generated callers. */
@Deprecated(forRemoval = false)
public class LowPlanetGrass_InstallationProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return world.getBlockState(BlockPos.containing(x, y - 1, z)).is(Ssc14Blocks.GRASS_LIGHT_BLOCK.get());
	}
}
