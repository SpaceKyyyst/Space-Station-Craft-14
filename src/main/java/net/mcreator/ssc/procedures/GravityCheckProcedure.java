package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GravityCheckProcedure {
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
		if (entity == null)
			return;
		if (!(entity instanceof LivingEntity livingEntity))
			return;
		// === Создаём AIRS_TAG внутри метода (единственный способ для MCreator) ===
		TagKey<Block> AIRS_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.parse("ssc14:airs") // ← БЕЗ пробелов в конце!
		);
		// === 1. Проверка измерения ===
		boolean isInSpace = entity.level().dimension().equals(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced")));
		if (!isInSpace) {
			if (livingEntity.getAttributes().hasAttribute(Attributes.GRAVITY))
				livingEntity.getAttribute(Attributes.GRAVITY).setBaseValue(0.08);
			livingEntity.setNoGravity(false);
			return;
		}
		// === 2. Проверка гравитации станции ===
		if (!Ssc14ModVariables.station_gravity) {
			if (livingEntity.getAttributes().hasAttribute(Attributes.GRAVITY))
				livingEntity.getAttribute(Attributes.GRAVITY).setBaseValue(0);
			livingEntity.setNoGravity(true);
			return;
		}
		// === 3. Поиск пола (цикл вместо 7 if) ===
		int floorDistance = -1;
		BlockPos entityPos = entity.blockPosition();
		for (int i = 1; i <= 7; i++) {
			BlockState state = entity.level().getBlockState(entityPos.below(i));
			if (!state.is(AIRS_TAG)) {
				floorDistance = i;
				break;
			}
		}
		// === 4. Установка гравитации ===
		double gravity;
		boolean noGravity;
		if (floorDistance <= 0) {
			gravity = 0;
			noGravity = true;
		} else if (floorDistance <= 5) {
			gravity = 0.08;
			noGravity = false;
		} else if (floorDistance <= 6) {
			gravity = 0.04;
			noGravity = false;
		} else if (floorDistance <= 7) {
			gravity = 0.01;
			noGravity = false;
		} else {
			gravity = 0;
			noGravity = true;
		}
		if (livingEntity.getAttributes().hasAttribute(Attributes.GRAVITY))
			livingEntity.getAttribute(Attributes.GRAVITY).setBaseValue(gravity);
		livingEntity.setNoGravity(noGravity);
		//НИЖЕ КОСТЫЛЬ, не трогать, нужен для MC
		if (Level.END == (entity.level().dimension())) {
			entity.setNoGravity(false);
		}
	}
}