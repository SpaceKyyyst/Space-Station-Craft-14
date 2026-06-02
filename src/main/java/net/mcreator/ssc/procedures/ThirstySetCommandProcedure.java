package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.ssc.init.Ssc14ModAttributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class ThirstySetCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		if ((commandParameterEntity(arguments, "ent")) instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Ssc14ModAttributes.LIQUID))
			_livingEntity2.getAttribute(Ssc14ModAttributes.LIQUID).setBaseValue((DoubleArgumentType.getDouble(arguments, "Liquid")));
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