package net.ssc14.content.block.control;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** Integration boundary for the legacy network-button activation flow. */
public final class NetworkButtonInteractionService {
    private NetworkButtonInteractionService() { }

    public static void activate(Level level, BlockPos pos) {
        if (level != null && level.isClientSide()) return;
        if (level != null && pos != null) {
            ButtonInteractionService.execute(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
