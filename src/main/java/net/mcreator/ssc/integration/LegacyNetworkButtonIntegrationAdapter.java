package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ssc14.content.block.control.NetworkButtonInteractionService;
import net.ssc14.content.block.control.ButtonInteractionService;

/** Bridges the canonical button to its historical activation procedure. */
public final class LegacyNetworkButtonIntegrationAdapter
        implements NetworkButtonInteractionService.Adapter {
    @Override
    public void activate(Level level, BlockPos pos) {
        ButtonInteractionService.execute(
                level, pos.getX(), pos.getY(), pos.getZ());
    }
}
