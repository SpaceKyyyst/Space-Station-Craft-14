package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.init.Ssc14ModItems;

public class Extinguisher_OpenProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getItem() == Ssc14ModItems.EXTINGUISHER.get()) {
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack4 = new ItemStack(Ssc14ModItems.EXTINGUISHER_OPEN.get()).copy();
				_setstack4.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack4);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_flask_close1")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_flask_close1")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 5);
		} else if (itemstack.getItem() == Ssc14ModItems.EXTINGUISHER_OPEN.get() && entity.isShiftKeyDown()) {
			itemstack.shrink(1);
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack18 = new ItemStack(Ssc14ModItems.EXTINGUISHER.get()).copy();
				_setstack18.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack18);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_flask_close1")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:items_flask_close1")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 5);
		} else if (itemstack.getItem() == Ssc14ModItems.EXTINGUISHER_OPEN.get() && !entity.isShiftKeyDown()) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:extinguish_effect")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:extinguish_effect")), SoundSource.MASTER,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			if (world instanceof ServerLevel serverLevel && entity instanceof net.minecraft.world.entity.LivingEntity living) {
				net.minecraft.world.phys.Vec3 look = living.getLookAngle().normalize();
				// БАЗОВАЯ точка спавна — округляем до целого, чтобы дробная часть была "чистой"
				double baseX = Math.round(living.getX() - 0.5);
				double baseY = Math.round(living.getY() + living.getBbHeight() / 1.25);
				double baseZ = Math.round(living.getZ() - 0.5);
				for (int i = 0; i < 10; i++) {
					// 🎯 Уникальный вектор для каждой частицы
					double spread = 0.25;
					double vx = look.x + (serverLevel.getRandom().nextFloat() - 0.5f) * spread;
					double vy = look.y + (serverLevel.getRandom().nextFloat() - 0.5f) * spread;
					double vz = look.z + (serverLevel.getRandom().nextFloat() - 0.5f) * spread;
					net.minecraft.world.phys.Vec3 dir = new net.minecraft.world.phys.Vec3(vx, vy, vz).normalize();
					float speedVar = 0.5f + serverLevel.getRandom().nextFloat() * 0.4f;
					net.minecraft.world.phys.Vec3 velocity = dir.scale(0.7 * speedVar);
					// 🛠 КОДЕК: Кодируем скорость в дробную часть
					// 0.7 * 0.01 = 0.007 (невидимое смещение)
					// Диапазон скорости [-1.0; 1.0] → смещение [-0.01; 0.01]
					serverLevel.sendParticles((net.minecraft.core.particles.SimpleParticleType) Ssc14ModParticleTypes.EXTINGUISHER_SPRAY.get(), baseX + velocity.x * 0.01, baseY + velocity.y * 0.01, baseZ + velocity.z * 0.01, 1, 0.0, 0.0, 0.0, 0.0);
				}
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 20);
		}
	}
}