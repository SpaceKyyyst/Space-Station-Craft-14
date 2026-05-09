package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class HealthUIanimateProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static double execute(Entity entity) {
		return execute(null, entity);
	}

	private static double execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return 0;
		if (entity.getPersistentData().getDoubleOr("health_ui_timer", 0) < 112) {
			entity.getPersistentData().putDouble("health_ui_timer", (1 + entity.getPersistentData().getDoubleOr("health_ui_timer", 0)));
		} else {
			entity.getPersistentData().putDouble("health_ui_timer", 0);
		}
		return Math.round(entity.getPersistentData().getDoubleOr("health_ui_timer", 0) / 4);
	}
}