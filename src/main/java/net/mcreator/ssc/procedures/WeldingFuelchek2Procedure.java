package net.mcreator.ssc.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;

public class WeldingFuelchek2Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double fuel = 0;
		if (entity instanceof ServerPlayer _player)
			_player.sendSystemMessage(
					Component.literal(
							("Fuel:  " + Math.round(((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0) - 1) / 100))),
					true);
	}
}