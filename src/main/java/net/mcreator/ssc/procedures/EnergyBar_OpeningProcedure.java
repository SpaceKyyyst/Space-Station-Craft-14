package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.ssc14.content.item.food.EnergyBarOpeningService;

/** @deprecated Use {@link EnergyBarOpeningService}; retained for generated callers. */
@Deprecated(forRemoval = false)
public class EnergyBar_OpeningProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		EnergyBarOpeningService.open(world, x, y, z, entity, itemstack);
	}
}
