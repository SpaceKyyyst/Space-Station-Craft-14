package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class PlayerScaleRedactProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.SCALE))
			_livingEntity0.getAttribute(Attributes.SCALE).setBaseValue(0.9);
		/*
		MINECRAFT атрибут scale:
		* Люди: 0,88 - 1,05
		* Дворфы: 0,50 - 0,72
		* Унатхи: 0,99 - 1,22
		* Слаймолюды: 0,55 - 0,88
		* Дионы: 0,55 - 1,22
		* Арахниды: 1,38 - 1,93
		* Нианы: 0,94
		* Воксы: 0,55 - 0,83
		* Вульпы: 0,94 - 1,10
		* Фелиниды: 0,72 - 0,88
		*/
	}
}