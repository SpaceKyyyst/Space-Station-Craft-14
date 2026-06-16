
package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.Commands;

import net.mcreator.ssc.ModReagents;
import net.mcreator.ssc.procedures.FillReagentPRProcedure;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

// Просто @EventBusSubscriber без параметров
@EventBusSubscriber
public class SSC14ChemistryCommandCommand {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("ssc14_chemistry")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("fill_reagent")
                        .then(Commands.argument("reagent_name", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    for (ModReagents.Reagent reagent : ModReagents.getAllReagents()) {
                                        builder.suggest(reagent.getId());
                                    }
                                    return builder.buildFuture();
                                })
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();
                                            if (entity == null && world instanceof ServerLevel _servLevel)
                                                entity = FakePlayerFactory.getMinecraft(_servLevel);
                                            Direction direction = Direction.DOWN;
                                            if (entity != null)
                                                direction = entity.getDirection();

                                            FillReagentPRProcedure.execute(arguments);
                                            return 0;
                                        })
                                )
                        )
                )
        );
    }
}
