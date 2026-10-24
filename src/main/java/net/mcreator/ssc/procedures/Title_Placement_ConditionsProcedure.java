package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.floor.FloorTileSupportPolicy;

/** @deprecated Use {@link FloorTileSupportPolicy}. */
@Deprecated
public class Title_Placement_ConditionsProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return FloorTileSupportPolicy.canSupport(world, BlockPos.containing(x, y, z));
	}
}
