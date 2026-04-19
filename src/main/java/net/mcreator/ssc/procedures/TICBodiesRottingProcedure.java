package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.particles.ParticleTypes;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TICBodiesRottingProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		// ServerTickEvent выполняется ТОЛЬКО на сервере — проверок не нужно
		// Получаем сервер через NeoForge-хук (работает в 1.21.8)
		net.minecraft.server.MinecraftServer server = net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer();
		if (server == null)
			return;
		// Итерируемся по всем игрокам
		for (net.minecraft.server.level.ServerPlayer player : server.getPlayerList().getPlayers()) {
			if (player == null)
				continue;
			var nbt = player.getPersistentData();
			int state = nbt.getInt("ssc14_state").orElse(0);
			// Запускаем таймер гниения при переходе в состояние "мёртв"
			if (state == 2 && !nbt.getBoolean("ssc14_decayStarted").orElse(false)) {
				// Для теста: 100 тиков = 5 секунд. Для релиза: замени на 12000 (10 минут)
				long decayStartTick = player.level().getGameTime() + 100;
				nbt.putLong("ssc14_decayStartTick", decayStartTick);
				nbt.putBoolean("ssc14_decayStarted", true);
				System.out.println("[SSC14] 🧟 Гниение началось: " + player.getName().getString());
			}
			// Наносим пассивный урон каждые 100 тиков (5 секунд)
			if (nbt.getBoolean("ssc14_decayStarted").orElse(false)) {
				long currentTick = player.level().getGameTime();
				long lastDecayTick = nbt.getLong("ssc14_lastDecayTick").orElse(0L);
				if (currentTick - lastDecayTick >= 100) {
					double blunt = nbt.getDouble("ssc14_dmg_blunt").orElse(0.0) + 0.06;
					double cellular = nbt.getDouble("ssc14_dmg_cellular").orElse(0.0) + 0.06;
					double total = nbt.getDouble("ssc14_damage").orElse(0.0) + 0.12;
					nbt.putDouble("ssc14_dmg_blunt", blunt);
					nbt.putDouble("ssc14_dmg_cellular", cellular);
					nbt.putDouble("ssc14_damage", total);
					nbt.putLong("ssc14_lastDecayTick", currentTick);
					System.out.println("[SSC14] 🧟 Гниение тик: +0.06 blunt +0.06 cellular | " + player.getName().getString());
					// Проверка на полное разложение
					if (cellular > 200 && !nbt.getBoolean("ssc14_gibbed").orElse(false)) {
						nbt.putBoolean("ssc14_gibbed", true);
						System.out.println("[SSC14] 💀 Тело " + player.getName().getString() + " разложилось полностью");
					}
				}
			}
		}
		if (false) {
			world.addParticle(ParticleTypes.ASH, x, y, z, 0, 0, 0);
		}
	}
}