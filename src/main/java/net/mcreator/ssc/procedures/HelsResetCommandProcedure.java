package net.mcreator.ssc.procedures;

import org.checkerframework.checker.units.qual.t;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

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
		// Удаляем основные флаги
		nbt.remove("ssc14_damage");
		nbt.remove("ssc14_state");
		nbt.remove("ssc14_bleeding");
		// Удаляем все счётчики по типам урона
		String[] types = {"blunt", "slash", "piercing", "heat", "shock", "cold", "caustic", "poison", "radiation", "asphyx", "bloodloss", "cellular"};
		for (String t : types) {
			nbt.remove("ssc14_dmg_" + t);
		}
		// Убираем эффекты крита/смерти
		player.removeAllEffects();
		player.setInvisible(false);
		if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
			sp.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
		}
		System.out.println("[SSC14] 🛠 Полный сброс применён к: " + player.getName().getString());
		(commandParameterEntity(arguments, "ent")).makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
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