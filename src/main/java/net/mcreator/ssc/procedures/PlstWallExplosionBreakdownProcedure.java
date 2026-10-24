package net.mcreator.ssc.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.wall.WallDamageService;

/** @deprecated Use {@link WallDamageService}. */
@Deprecated(forRemoval = false)
public class PlstWallExplosionBreakdownProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel level) {
			WallDamageService.schedulePlasteelExplosion(level, x, y, z);
		}
	}
}
