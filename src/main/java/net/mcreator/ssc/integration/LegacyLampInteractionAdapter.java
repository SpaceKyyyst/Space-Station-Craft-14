package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.energy.LampInteractionService;

/** Compatibility boundary for the legacy lamp replacement procedure. */
public final class LegacyLampInteractionAdapter {
    private LegacyLampInteractionAdapter() {
    }

    public static void interact(Level level, BlockPos pos, BlockState state, Player player) {
        LampInteractionService.execute(level, pos.getX(), pos.getY(), pos.getZ(), state, player);
    }
}
