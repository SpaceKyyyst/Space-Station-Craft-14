package net.mcreator.ssc.procedures;

import org.checkerframework.checker.units.qual.t;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OnPlayerTakeDamageSSC14Procedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingDamageEvent.Pre event) {
		if (event.getEntity() != null) {
			execute(event);
		}
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
		// === ПРЕАМБУЛА ===
		if (!(event instanceof net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre damageEvent))
			return;
		if (!(damageEvent.getEntity() instanceof net.minecraft.world.entity.player.Player player))
			return;
		if (player.level().isClientSide)
			return;
		damageEvent.setNewDamage(0);
		var nbt = player.getPersistentData();
		float incoming = damageEvent.getOriginalDamage();
		var source = damageEvent.getSource();
		// === ШАГ 1: Определяем тип урона ===
		String damageType = "blunt";
		String msgId = source.type().msgId();
		if (msgId.contains("player_attack") || msgId.contains("mob_attack")) {
			var item = player.getMainHandItem();
			if (!item.isEmpty()) {
				boolean isSlash = item.getTags().anyMatch(t -> t.location().getPath().contains("slash") || t.location().getPath().contains("sword") || t.location().getPath().contains("axe"));
				boolean isPiercing = item.getTags().anyMatch(t -> t.location().getPath().contains("piercing") || t.location().getPath().contains("trident") || t.location().getPath().contains("arrow"));
				damageType = isSlash ? "slash" : (isPiercing ? "piercing" : "blunt");
			}
		} else if (msgId.equals("fall") || msgId.equals("cactus") || msgId.equals("anvil") || msgId.equals("falling_block") || msgId.equals("fly_into_wall")) {
			damageType = "blunt";
		} else if (msgId.equals("in_fire") || msgId.equals("on_fire") || msgId.equals("lava") || msgId.equals("hot_floor") || msgId.equals("fireworks")) {
			damageType = "heat";
		} else if (msgId.equals("lightning_bolt")) {
			damageType = "shock";
		} else if (msgId.equals("arrow") || msgId.equals("trident") || msgId.equals("thorns") || msgId.equals("magic") || msgId.equals("indirect_magic")) {
			damageType = "piercing";
		} else if (msgId.equals("poison") || msgId.equals("wither") || msgId.equals("dragon_breath")) {
			damageType = "poison";
		} else if (msgId.equals("freeze")) {
			damageType = "cold";
		}
		// === ШАГ 2: Накатываем урон ===
		String typeKey = "ssc14_dmg_" + damageType;
		double typeDamage = nbt.getDouble(typeKey).orElse(0.0) + incoming;
		nbt.putDouble(typeKey, typeDamage);
		double totalDamage = nbt.getDouble("ssc14_damage").orElse(0.0) + incoming;
		nbt.putDouble("ssc14_damage", totalDamage);
		System.out.println("[SSC14] +" + incoming + " " + damageType + " | Всего: " + totalDamage + " | " + typeKey + ": " + typeDamage);
		// === ШАГ 3: Замедление по порогам (60 / 80) ===
		// В 1.21.8: SLOWNESS вместо MOVEMENT_SLOWDOWN, SPEED вместо MOVEMENT_SPEED
		player.removeEffect(net.minecraft.world.effect.MobEffects.SLOWNESS);
		if (totalDamage >= 80) {
			player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOWNESS, 200, 4, false, false)); // -50%
		} else if (totalDamage >= 60) {
			player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOWNESS, 200, 2, false, false)); // -30%
		}
		// === ШАГ 4: Проверка на "расчленение" (Gibbing) ===
		double blunt = nbt.getDouble("ssc14_dmg_blunt").orElse(0.0);
		double cellular = nbt.getDouble("ssc14_dmg_cellular").orElse(0.0);
		double heat = nbt.getDouble("ssc14_dmg_heat").orElse(0.0);
		if (blunt > 400 || cellular > 200) {
			if (!nbt.getBoolean("ssc14_gibbed").orElse(false)) {
				nbt.putBoolean("ssc14_gibbed", true);
				// В 1.21.8 kill() требует ServerLevel, используем hurt с максимальным уроном
				player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
				System.out.println("[SSC14] 💥 РАСЧЛЕНЕНИЕ (blunt/cellular)");
			}
			return;
		}
		if (heat > 1500) {
			if (!nbt.getBoolean("ssc14_ash").orElse(false)) {
				nbt.putBoolean("ssc14_ash", true);
				player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
				System.out.println("[SSC14] 🔥 ПРЕВРАЩЕНИЕ В ПЕПЕЛ");
			}
			return;
		}
		// === ШАГ 5: Критическое состояние и смерть ===
		int state = nbt.getInt("ssc14_state").orElse(0);
		if ((totalDamage > 200 || blunt > 300 || heat > 1000) && state != 2) {
			nbt.putInt("ssc14_state", 2);
			if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
				sp.setGameMode(net.minecraft.world.level.GameType.SPECTATOR);
			}
			player.removeAllEffects();
			player.setInvisible(true);
			System.out.println("[SSC14] 💀 МЕРТВ");
		} else if (totalDamage >= 60 && state != 1) {
			nbt.putInt("ssc14_state", 1);
			player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOWNESS, 200, 3, false, false));
			player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 200, 0, false, false));
			player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 2, false, false));
			System.out.println("[SSC14] 🚨 КРИТ");
		} else if (totalDamage < 60 && state != 0) {
			nbt.putInt("ssc14_state", 0);
			player.removeEffect(net.minecraft.world.effect.MobEffects.SLOWNESS);
			player.removeEffect(net.minecraft.world.effect.MobEffects.BLINDNESS);
			player.removeEffect(net.minecraft.world.effect.MobEffects.WEAKNESS);
			player.setInvisible(false);
			if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
				sp.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
			}
			System.out.println("[SSC14] 💚 ВОССТАНОВЛЕНИЕ");
		}
		// === ШАГ 6: Кровотечение от slash-урона ===
		if (damageType.equals("slash") && typeDamage > 10 && !nbt.getBoolean("ssc14_bleeding").orElse(false)) {
			nbt.putBoolean("ssc14_bleeding", true);
			System.out.println("[SSC14] 🩸 Началось кровотечение!");
		}
	}
}