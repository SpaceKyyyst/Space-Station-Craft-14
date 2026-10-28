package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.ShuttersIntegrationService;
import net.ssc14.content.block.airlock.ShuttersAnimationService;
import net.ssc14.content.block.airlock.ShuttersInteractionService;
import net.ssc14.content.block.airlock.ShuttersStateService;

/** Bridges canonical shutters to their historical procedures. */
public final class LegacyShuttersIntegrationAdapter
        implements ShuttersIntegrationService.Adapter {
    @Override public void update(Level level, BlockPos pos, BlockState state) {
        ShuttersStateService.update(level, pos, state);
    }

    @Override public void use(Level level, BlockPos pos, BlockState state, Player player) {
        ShuttersInteractionService.use(level, pos, state, player);
    }

    @Override public void setOpen(Level level, BlockPos pos, BlockState state, boolean open) {
        if (open) {
            ShuttersAnimationService.setOpen(level, pos, true);
        } else {
            ShuttersAnimationService.setOpen(level, pos, false);
        }
    }
}
