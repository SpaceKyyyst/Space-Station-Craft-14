package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Bridges canonical shutters services while the historical block entry points remain. */
public final class LegacyShuttersIntegrationAdapter implements ShuttersIntegrationService.Adapter {
    @Override
    public void update(Level level, BlockPos pos, BlockState state) {
        ShuttersStateService.update(level, pos, state);
    }

    @Override
    public void use(Level level, BlockPos pos, BlockState state, Player player) {
        ShuttersInteractionService.use(level, pos, state, player);
    }

    @Override
    public void setOpen(Level level, BlockPos pos, BlockState state, boolean open) {
        ShuttersAnimationService.setOpen(level, pos, open);
    }
}
