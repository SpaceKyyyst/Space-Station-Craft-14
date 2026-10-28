
package net.ssc14.content.block.airlock;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import net.mcreator.ssc.procedures.UniversalDeconstructionProcedureProcedure;

@EventBusSubscriber
public final class AirlockToolInteractionHandler {

	private AirlockToolInteractionHandler() {
	}
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != InteractionHand.MAIN_HAND)
			return;
			
		// Проверка: выполняем код ТОЛЬКО на стороне сервера
		if (event.getLevel().isClientSide())
			return;
			
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (world == null || entity == null)
			return;

		BlockPos pos = BlockPos.containing(x, y, z);
		if (entity instanceof net.minecraft.world.entity.player.Player player
				&& (net.mcreator.ssc.access.AirlockEventLog.inspect(player, pos)
                    || net.mcreator.ssc.access.AirlockMaintenance.interact(player, pos)
                    || AirlockWeldingService.interact(player, pos)
                    || net.mcreator.ssc.access.AirlockManualOpening.interact(player, pos)
                    || net.ssc14.content.block.airlock.AirlockFloor.installTile(player, pos)

                    || AirlockDisassemblyService.interact(player, pos))) {
			if (event instanceof PlayerInteractEvent.RightClickBlock interaction) {
				interaction.setCanceled(true);
				interaction.setCancellationResult(net.minecraft.world.InteractionResult.SUCCESS);
			}
			return;
		}
		pos = net.mcreator.ssc.access.AirlockMaintenance.root(world, pos);
        BlockState blockstate = world.getBlockState(pos);

        // Tool work must not also trigger the door's ordinary right-click opening.
        if (entity instanceof net.minecraft.world.entity.player.Player player
                && player.getMainHandItem().is(net.mcreator.ssc.init.Ssc14ModItems.SCREWDRIVER.get())
                && blockstate.getBlock().getStateDefinition().getProperty("arl_variat") != null) {
            if (player.isAlive() && !player.isSpectator() && player.level().mayInteract(player, pos))
                UniversalDeconstructionProcedureProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate, entity);
            if (event instanceof PlayerInteractEvent.RightClickBlock interaction) {
                interaction.setCanceled(true);
                interaction.setCancellationResult(net.minecraft.world.InteractionResult.SUCCESS);
            }
            return;
        }

		UniversalDeconstructionProcedureProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate, entity);
	}
}
