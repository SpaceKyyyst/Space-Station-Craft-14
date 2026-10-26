package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ssc14.content.block.control.NetworkButtonInteractionService;

/** Bridges the canonical button to its historical activation procedure. */
public final class LegacyNetworkButtonIntegrationAdapter
        implements NetworkButtonInteractionService.Adapter {
    @Override
    public void activate(Level level, BlockPos pos) {
        net.mcreator.ssc.procedures.Button_CLICK_Procedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ());
    }
}
