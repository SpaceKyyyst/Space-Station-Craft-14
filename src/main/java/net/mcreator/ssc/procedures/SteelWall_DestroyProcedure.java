package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.wall.WallDamageService;

/** @deprecated Use {@link WallDamageService}. */
@Deprecated(forRemoval = false)
public class SteelWall_DestroyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		WallDamageService.breakByPlayer(world, x, y, z, entity);
	}
}
