package net.mcreator.ssc.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.wall.WallDamageService;

/** @deprecated Use {@link WallDamageService}. */
@Deprecated(forRemoval = false)
public class Wall_Explosion_BreakdownProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel level) {
			WallDamageService.scheduleSteelExplosion(level, x, y, z);
		}
	}
}
