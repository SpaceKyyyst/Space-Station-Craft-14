package net.mcreator.ssc.procedures;

import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class LiquidGetCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		if ((commandParameterMessage(arguments, "liquid_name")).equals("")) {/*code*/
		} else if (DoubleArgumentType.getDouble(arguments, "count") == 0) {/*code*/
		}
	}

	private static String commandParameterMessage(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return MessageArgument.getMessage(arguments, parameter).getString();
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}
}