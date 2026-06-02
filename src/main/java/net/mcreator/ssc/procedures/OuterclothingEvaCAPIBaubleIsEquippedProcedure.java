package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class OuterclothingEvaCAPIBaubleIsEquippedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity) {
			AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse("ssc_14:outerclothing"),
					(0 - (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity0.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() : 0) * 0.2),
					AttributeModifier.Operation.ADD_VALUE);
			if (!_entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(modifier.id())) {
				_entity.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(modifier);
			}
		}
	}
}