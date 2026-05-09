package net.mcreator.ssc.procedures;

import org.checkerframework.checker.units.qual.t;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.ssc.init.Ssc14ModAttributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class HelsResetCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		net.minecraft.world.entity.Entity target = commandParameterEntity(arguments, "ent");
		if (target == null || target.level().isClientSide())
			return;
		if (!(target instanceof net.minecraft.world.entity.player.Player player)) {
			System.out.println("[SSC14] ⚠️ Сброс не выполнен: цель не игрок.");
			return;
		}
		var nbt = player.getPersistentData();
		// 🔧 Сбрасываем основной счётчик урона
		nbt.remove("sscCustomHealth");
		// 🔧 Удаляем ВСЕ флаги состояний (добавлены ssc14_dead и ssc14_critical)
		nbt.remove("ssc14_state");
		nbt.remove("ssc14_bleeding");
		nbt.remove("ssc14_gibbed");
		nbt.remove("ssc14_ash");
		nbt.remove("ssc14_decayStarted");
		nbt.remove("ssc14_lastDecayTick");
		nbt.remove("ssc14_lastBleedTick");
		nbt.remove("ssc14_critical"); // 🔹 Добавлено
		nbt.remove("ssc14_dead"); // 🔹 ГЛАВНОЕ: флаг смерти!
		nbt.remove("ssc14_asphyxTimer"); // 🔹 Таймер удушья
		nbt.remove("ssc14_regenTimer"); // 🔹 Таймер регенерации (если есть)
		nbt.remove("ssc14_startPos"); // 🔹 Позиция для лечения
		nbt.remove("ssc14_progressBar"); // 🔹 Прогресс-бар медикаментов
		// Удаляем все счётчики по типам урона
		String[] types = {"blunt", "slash", "piercing", "heat", "shock", "cold", "caustic", "poison", "radiation", "asphyx", "bloodloss", "cellular"};
		for (String t : types) {
			nbt.remove("ssc14_dmg_" + t);
		}
		// Убираем эффекты и визуальные состояния
		player.removeAllEffects();
		player.setInvisible(false);
		// 🔧 Сбрасываем UI-атрибут здоровья на 0
		if (player.getAttributes().hasAttribute(net.mcreator.ssc.init.Ssc14ModAttributes.HEALTH_U_IATTRIBUTE)) {
			var uiAttr = player.getAttribute(net.mcreator.ssc.init.Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
			if (uiAttr != null)
				uiAttr.setBaseValue(0);
		}
		// Возвращаем режим выживания
		if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
			sp.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
		}
		// Сбрасываем модификатор скорости
		var speedAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
		if (speedAttr != null && speedAttr.hasModifier(net.minecraft.resources.ResourceLocation.parse("ssc14:slowdown"))) {
			speedAttr.removeModifier(net.minecraft.resources.ResourceLocation.parse("ssc14:slowdown"));
		}
		// 🔧 Сбрасываем mayBuild (на случай, если был в крите)
		player.getAbilities().mayBuild = true;
		player.onUpdateAbilities();
		System.out.println("[SSC14] 🛠 Полный сброс применён к: " + player.getName().getString());
		if ((commandParameterEntity(arguments, "ent")) instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE))
			_livingEntity1.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE).setBaseValue(0);
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}