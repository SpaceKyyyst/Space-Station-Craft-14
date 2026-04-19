package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.init.Ssc14ModAttributes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class MetabolismTICProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS)
				&& !(entity instanceof Player _plr1 && _plr1.gameMode() == GameType.CREATIVE || entity instanceof Player _plr2 && _plr2.gameMode() == GameType.SPECTATOR)) {
			if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS))
				_livingEntity4.getAttribute(Ssc14ModAttributes.NUTRIENTS).setBaseValue((Math.round(
						((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Ssc14ModAttributes.NUTRIENTS) ? _livingEntity3.getAttribute(Ssc14ModAttributes.NUTRIENTS).getValue() : 0) - 0.00084) * 100000)
						/ 100000d));
		}
	}
}