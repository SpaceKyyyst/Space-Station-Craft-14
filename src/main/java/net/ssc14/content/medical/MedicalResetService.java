
package net.ssc14.content.medical;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.ssc14.content.attribute.Ssc14Attributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public final class MedicalResetService {
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
		// Release only effects and pose acquired by medical critical state.
		MedicalPlayerEffects.release(player);
		// 🔧 Сбрасываем UI-атрибут здоровья на 0
		if (player.getAttributes().hasAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE)) {
			var uiAttr = player.getAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE);
			if (uiAttr != null)
				uiAttr.setBaseValue(0);
		}
		// Сбрасываем модификатор скорости
		var speedAttr = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
		if (speedAttr != null && speedAttr.hasModifier(net.minecraft.resources.Identifier.parse("ssc14:slowdown"))) {
			speedAttr.removeModifier(net.minecraft.resources.Identifier.parse("ssc14:slowdown"));
		}
		System.out.println("[SSC14] 🛠 Полный сброс применён к: " + player.getName().getString());
		if ((commandParameterEntity(arguments, "ent")) instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE))
			_livingEntity1.getAttribute(Ssc14Attributes.HEALTH_U_IATTRIBUTE).setBaseValue(0);
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
