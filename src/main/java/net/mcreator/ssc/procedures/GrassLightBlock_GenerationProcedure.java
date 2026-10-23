package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.ssc14.content.block.planetary.PlanetaryBlockStateService;

/** @deprecated Compatibility adapter for generated callers. */
@Deprecated(forRemoval = false)
public class GrassLightBlock_GenerationProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		PlanetaryBlockStateService.randomizeOrientation(world, BlockPos.containing(x, y, z));
	}
}
