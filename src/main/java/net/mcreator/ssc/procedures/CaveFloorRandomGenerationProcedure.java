package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.geology.CaveFloorRandomizer;

/** @deprecated Use {@link CaveFloorRandomizer}. */
@Deprecated(forRemoval = false)
public class CaveFloorRandomGenerationProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		CaveFloorRandomizer.replace(world, BlockPos.containing(x, y, z));
	}
}
