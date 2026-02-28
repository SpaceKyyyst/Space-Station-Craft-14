package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
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
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced")) == (entity.level().dimension())) {
			if (true == Ssc14ModVariables.station_gravity) {
				if (!(world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))
						|| !(world.getBlockState(BlockPos.containing(x, y - 2, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))
						|| !(world.getBlockState(BlockPos.containing(x, y - 3, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))
						|| !(world.getBlockState(BlockPos.containing(x, y - 4, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))
						|| !(world.getBlockState(BlockPos.containing(x, y - 5, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))) {
					if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.GRAVITY))
						_livingEntity13.getAttribute(Attributes.GRAVITY).setBaseValue(0.08);
					entity.setNoGravity(false);
				} else if (!(world.getBlockState(BlockPos.containing(x, y - 6, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))) {
					if (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.GRAVITY))
						_livingEntity17.getAttribute(Attributes.GRAVITY).setBaseValue(0.04);
				} else if (!(world.getBlockState(BlockPos.containing(x, y - 7, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))) {
					if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.GRAVITY))
						_livingEntity20.getAttribute(Attributes.GRAVITY).setBaseValue(0.01);
				} else {
					if (entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.GRAVITY))
						_livingEntity21.getAttribute(Attributes.GRAVITY).setBaseValue(0);
					entity.setNoGravity(true);
				}
			} else {
				if (entity instanceof LivingEntity _livingEntity23 && _livingEntity23.getAttributes().hasAttribute(Attributes.GRAVITY))
					_livingEntity23.getAttribute(Attributes.GRAVITY).setBaseValue(0);
				entity.setNoGravity(true);
			}
		} else {
			if (entity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(Attributes.GRAVITY))
				_livingEntity25.getAttribute(Attributes.GRAVITY).setBaseValue(0.08);
			entity.setNoGravity(false);
		}
	}
}