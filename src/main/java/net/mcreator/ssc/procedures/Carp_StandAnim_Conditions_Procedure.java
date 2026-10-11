package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;

public class Carp_StandAnim_Conditions_Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity.onGround()) {
			return true;
		}
		return false;
	}
}