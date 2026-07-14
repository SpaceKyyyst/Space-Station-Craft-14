package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;

public class DECAL_setting_Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity.isShiftKeyDown()) {
			return true;
		}
		return false;
	}
}