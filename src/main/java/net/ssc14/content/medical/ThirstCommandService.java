package net.ssc14.content.medical;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.ssc14.content.attribute.Ssc14Attributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public final class ThirstCommandService {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		if ((commandParameterEntity(arguments, "ent")) instanceof LivingEntity living
				&& living.getAttributes().hasAttribute(Ssc14Attributes.LIQUID))
			living.getAttribute(Ssc14Attributes.LIQUID)
					.setBaseValue(DoubleArgumentType.getDouble(arguments, "Liquid"));
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
