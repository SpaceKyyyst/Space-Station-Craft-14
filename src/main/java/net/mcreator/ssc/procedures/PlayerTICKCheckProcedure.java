package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModAttributes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerTICKCheckProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE))
			_livingEntity0.getAttribute(Attributes.SCALE).setBaseValue(0.75);
		if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.GAS_ANALYSER.get())
				&& !((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Ssc14ModItems.GAS_ANALYSER.get())
				&& (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB) ? _livingEntity5.getAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB).getValue() : 0) == 1) {
			if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB))
				_livingEntity6.getAttribute(Ssc14ModAttributes.GAS_AN_GU_IATRIB).setBaseValue(0);
		}
	}
}