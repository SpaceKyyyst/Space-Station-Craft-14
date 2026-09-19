
package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.mcreator.ssc.procedures.FillReagentPRProcedure;
import net.mcreator.ssc.ModReagents;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

@net.neoforged.fml.common.EventBusSubscriber
public class SSC14ChemistryCommandCommand {
	@net.neoforged.bus.api.SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("ssc14_chemistry")
				// ИСПРАВЛЕНО: Полный перенос синтаксиса проверки прав из твоей работающей шпаргалки
				.requires(Commands.hasPermission(Commands.LEVEL_ADMINS))
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
