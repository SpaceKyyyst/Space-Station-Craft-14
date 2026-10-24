package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.ceiling.CeilingSupportPolicy;

/** @deprecated Use {@link CeilingSupportPolicy}. */
@Deprecated
public class CeilingPlacementConditionsProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return CeilingSupportPolicy.isSuitableSupport(world, BlockPos.containing(x, y + 1, z));
	}
}
