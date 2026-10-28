package net.ssc14.content.decal;

import net.minecraft.world.entity.Entity;

public final class DecalToolPermissionService {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity.isShiftKeyDown()) {
			return true;
		}
		return false;
	}
}
