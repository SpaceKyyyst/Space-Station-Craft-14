package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.ShuttersIntegrationService;
import net.ssc14.content.block.airlock.ShuttersAnimationService;

/** Bridges canonical shutters to their historical procedures. */
public final class LegacyShuttersIntegrationAdapter
        implements ShuttersIntegrationService.Adapter {
    @Override public void update(Level level, BlockPos pos, BlockState state) {
        net.mcreator.ssc.procedures.Shutters_UPDATE_Procedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state);
    }

    @Override public void use(Level level, BlockPos pos, BlockState state, Player player) {
        net.mcreator.ssc.procedures.Shutters_DEBUD_AKTIVATOR_PR_Procedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state, player);
    }

    @Override public void setOpen(Level level, BlockPos pos, BlockState state, boolean open) {
        if (open) {
            ShuttersAnimationService.setOpen(level, pos, true);
        } else {
            ShuttersAnimationService.setOpen(level, pos, false);
        }
    }
}
