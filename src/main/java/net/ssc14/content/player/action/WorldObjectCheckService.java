package net.ssc14.content.player.action;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.core.BlockPos;

import net.ssc14.content.attribute.Ssc14Attributes;

public final class WorldObjectCheckService {
	public static boolean execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return false;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14Attributes.OPEN_WORLD_CHECK_MENU) ? _livingEntity0.getAttribute(Ssc14Attributes.OPEN_WORLD_CHECK_MENU).getValue() : 0) == 0
				|| (world.getBlockState(
						new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(),
								entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY(),
								entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())))
						.is(BlockTags.create(Identifier.parse("ssc14:airs")))) {
			return false;
		}
		return true;
	}
}
