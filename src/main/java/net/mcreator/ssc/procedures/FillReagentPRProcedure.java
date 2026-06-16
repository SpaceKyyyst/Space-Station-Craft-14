
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.ModReagents;
import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class FillReagentPRProcedure {
    public static void execute(CommandContext<CommandSourceStack> arguments) {
        ServerPlayer player;
        try {
            player = arguments.getSource().getPlayerOrException();
        } catch (CommandSyntaxException e) {
            arguments.getSource().sendFailure(Component.literal("§cКоманда должна быть выполнена игроком!"));
            return;
        }

        String reagentName = StringArgumentType.getString(arguments, "reagent_name");
        int amount = (int) DoubleArgumentType.getDouble(arguments, "amount");

        if (reagentName.isEmpty()) {
            player.sendSystemMessage(Component.literal("§cУкажите название реагента!"));
            return;
        }

        if (amount <= 0) {
            player.sendSystemMessage(Component.literal("§cКоличество должно быть больше 0!"));
            return;
        }

        ItemStack mainHand = player.getMainHandItem();

        if (!(mainHand.getItem() instanceof ReagentContainerItem)) {
            player.sendSystemMessage(Component.literal("§cВ главной руке должен быть контейнер для реагентов!"));
            return;
        }

        if (ModReagents.getReagent(reagentName).isEmpty()) {
            player.sendSystemMessage(Component.literal("§cНеизвестный реагент: " + reagentName));
            return;
        }

        boolean success = ModReagents.addReagent(mainHand, reagentName, amount);

        if (success) {
            ModReagents.processReactions(mainHand, player);
            String displayName = ModReagents.getReagent(reagentName).get().getDisplayName().getString();
            player.sendSystemMessage(Component.literal("§aДобавлено " + amount + " мл " + displayName));
        } else {
            player.sendSystemMessage(Component.literal("§cНе удалось добавить реагент (контейнер полон?)"));
        }
    }
}
