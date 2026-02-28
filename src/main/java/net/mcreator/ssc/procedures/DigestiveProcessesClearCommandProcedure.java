package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.ssc.init.Ssc14ModAttributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class DigestiveProcessesClearCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		if ((commandParameterEntity(arguments, "ent")) instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES))
			_livingEntity1.getAttribute(Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(0);
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