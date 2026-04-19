package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.ssc.network.Ssc14ModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GravityCheckProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean found = false;
		double sy = 0;
		// === 🎯 ИНИЦИАЛИЗАЦИЯ ===
		net.minecraft.nbt.CompoundTag data = entity.getPersistentData();
		final String P = "zg_v";
		final String ACTIVE = "zg_active";
		final String TICK = "zg_last_tick";
		final float DAMPING = 0.99f;
		// 🛡️ Grace period: первые 20 тиков → обычная гравитация
		if (entity.tickCount < 20) {
			entity.setNoGravity(false);
			data.remove(P + "x");
			data.remove(P + "y");
			data.remove(P + "z");
			data.remove(TICK);
			data.remove(ACTIVE);
			return;
		}
		// 🚫 Креатив/спектатор в полёте → ванильная физика
		if (entity instanceof net.minecraft.world.entity.player.Player player) {
			if (player.getAbilities().flying && (player.isCreative() || player.isSpectator())) {
				entity.setNoGravity(false);
				player.getAbilities().setWalkingSpeed(0.1F);
				player.getAbilities().setFlyingSpeed(0.05F);
				player.getAbilities().mayfly = player.isCreative();
				player.onUpdateAbilities();
				data.remove(P + "x");
				data.remove(P + "y");
				data.remove(P + "z");
				data.remove(TICK);
				data.remove(ACTIVE);
				return;
			}
		}
		// === 🔍 ОПРЕДЕЛЕНИЕ ГРАВИТАЦИИ (плоская логика) ===
		boolean hasGravity = true; // По умолчанию — гравитация есть
		// Проверяем измерение
		boolean inSpace = false;
		try {
			inSpace = entity.level().dimension().location().toString().equals("ssc_14:spaced");
		} catch (Exception e) {
			inSpace = false;
		}
		if (inSpace) {
			// Читаем глобальную переменную
			boolean stationGravity = false;
			try {
				stationGravity = net.mcreator.ssc.network.Ssc14ModVariables.station_gravity;
			} catch (Exception e) {
				stationGravity = true;
			}
			if (!stationGravity) {
				hasGravity = false; // Глобально выключена
			} else {
				// Ищем пол
				boolean foundFloor = false;
				for (int dy = 1; dy <= 7; dy++) {
					try {
						var pos = net.minecraft.core.BlockPos.containing(x, y - dy, z);
						if (entity.level().isLoaded(pos) && !entity.level().getBlockState(pos).isAir()) {
							foundFloor = true;
							break;
						}
					} catch (Exception e) {
						continue;
					}
				}
				if (!foundFloor)
					hasGravity = false; // Нет пола → невесомость
				// Если foundFloor == true → hasGravity остаётся true (по умолчанию)
			}
		}
		// Если !inSpace → hasGravity остаётся true (по умолчанию)
		// === 🔄 ВОССТАНОВЛЕНИЕ ФИЗИКИ ===
		if (hasGravity) {
			entity.setNoGravity(false);
			if (entity instanceof net.minecraft.world.entity.player.Player player) {
				player.getAbilities().setWalkingSpeed(0.1F);
				player.getAbilities().setFlyingSpeed(0.05F);
				player.getAbilities().mayfly = player.isCreative();
				player.onUpdateAbilities();
			}
			data.remove(P + "x");
			data.remove(P + "y");
			data.remove(P + "z");
			data.remove(TICK);
			data.remove(ACTIVE);
			return;
		}
		// === 🌌 НЕВЕСОМОСТЬ ===
		data.putBoolean(ACTIVE, true);
		if (data.contains(TICK) && data.getInt(TICK).orElse(0) == entity.tickCount)
			return;
		data.putInt(TICK, entity.tickCount);
		if (entity instanceof net.minecraft.world.entity.player.Player player) {
			player.getAbilities().setWalkingSpeed(0.0F);
			player.getAbilities().setFlyingSpeed(0.0F);
			player.onUpdateAbilities();
		}
		entity.setNoGravity(true);
		if (!data.contains(P + "x")) {
			var init = entity.getDeltaMovement();
			data.putDouble(P + "x", init.x);
			data.putDouble(P + "y", init.y);
			data.putDouble(P + "z", init.z);
		}
		var vel = new net.minecraft.world.phys.Vec3(data.getDouble(P + "x").orElse(0.0), data.getDouble(P + "y").orElse(0.0), data.getDouble(P + "z").orElse(0.0)).scale(DAMPING);
		var old = entity.position();
		entity.move(net.minecraft.world.entity.MoverType.SELF, vel);
		var actual = entity.position().subtract(old);
		entity.setDeltaMovement(actual);
		data.putDouble(P + "x", actual.x);
		data.putDouble(P + "y", actual.y);
		data.putDouble(P + "z", actual.z);
		if ((entity.level().dimension()) == Level.END && Ssc14ModVariables.station_gravity) {
			world.addParticle(ParticleTypes.ASH, x, y, z, 0, 0, 0);
			// КОСТЫЛЬ ¯\_(ツ)_/¯
		}
	}
}