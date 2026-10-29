package net.mcreator.ssc.integration;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.reagent.ReagentContainerAccess;
import net.ssc14.content.item.reagent.ReagentContainerItem;

/** Server-side command service for filling reagent containers. */
public final class ReagentFillCommandService {
    private ReagentFillCommandService() {
    }

    public static void execute(CommandContext<CommandSourceStack> context) {
        final ServerPlayer player;
        try {
            player = context.getSource().getPlayerOrException();
        } catch (CommandSyntaxException exception) {
            context.getSource().sendFailure(Component.literal("§cКоманда должна быть выполнена игроком!"));
            return;
        }

        String reagentName = StringArgumentType.getString(context, "reagent_name");
        int amount = (int) DoubleArgumentType.getDouble(context, "amount");
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
        if (LegacyReagentCatalog.find(reagentName).isEmpty()) {
            player.sendSystemMessage(Component.literal("§cНеизвестный реагент: " + reagentName));
            return;
        }

        if (ReagentContainerAccess.add(mainHand, reagentName, amount)) {
            net.mcreator.ssc.ModReagents.processReactions(mainHand, player);
            String displayName = LegacyReagentCatalog.find(reagentName).orElseThrow().getDisplayName().getString();
            player.sendSystemMessage(Component.literal("§aДобавлено " + amount + " мл " + displayName));
        } else {
            player.sendSystemMessage(Component.literal("§cНе удалось добавить реагент (контейнер полон?)"));
        }
    }
}
