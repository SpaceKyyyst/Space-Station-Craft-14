package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.Entity;

public class WOCtextPinnedProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		String loc_buf_t = "";
		if (entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.BLOCK) {
			return true;
		}
		return false;
	}
}