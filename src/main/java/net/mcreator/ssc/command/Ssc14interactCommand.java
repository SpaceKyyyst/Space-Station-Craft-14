
package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.mcreator.ssc.procedures.UniversalInteractCommandProcedure;

@EventBusSubscriber
public class Ssc14interactCommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("ssc14_interact")
                // ✅ Используем ResourceLocationArgument вместо StringArgumentType
                .then(Commands.argument("itemId", ResourceLocationArgument.id())
                    .executes(arguments -> {
                        // Получаем ID предмета напрямую как ResourceLocation
                        ResourceLocation rl = ResourceLocationArgument.getId(arguments, "itemId");
                        ServerPlayer player = arguments.getSource().getPlayer();

                        if (player != null) {
                            // Безопасно извлекаем Item из реестра (1.21.x совместимо)
                            Item item = BuiltInRegistries.ITEM.get(rl)
                                    .map(ref -> ref.value())
                                    .orElse(null);

                            if (item != null) {
                                UniversalInteractCommandProcedure.execute(player, item);
                            }
                        }
                        return 1;
                    })
                )
        );
    }
}
