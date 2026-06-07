
package net.mcreator.ssc.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

import net.mcreator.ssc.procedures.UniversalInteractCommandProcedure;

@EventBusSubscriber
public class Ssc14interactCommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("ssc14_interact")
                // ✅ Теперь ждём целое число (индекс слота)
                .then(Commands.argument("slotIndex", IntegerArgumentType.integer(0))
                    .executes(arguments -> {
                        int slotIndex = IntegerArgumentType.getInteger(arguments, "slotIndex");
                        ServerPlayer player = arguments.getSource().getPlayer();

                        if (player != null) {
                            UniversalInteractCommandProcedure.execute(player, slotIndex);
                        }
                        return 1;
                    })
                )
        );
    }
}
