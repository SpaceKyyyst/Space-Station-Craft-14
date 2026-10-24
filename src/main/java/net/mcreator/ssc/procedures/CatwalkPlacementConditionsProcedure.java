package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.catwalk.CatwalkSupportPolicy;

/** @deprecated Use {@link CatwalkSupportPolicy}. */
@Deprecated(forRemoval = false)
public final class CatwalkPlacementConditionsProcedure {
	private CatwalkPlacementConditionsProcedure() {
	}

	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return CatwalkSupportPolicy.canSupport(world, BlockPos.containing(x, y, z));
	}
}
