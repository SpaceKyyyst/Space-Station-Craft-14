package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.block.airlock.AirlockManualOpeningService;

/** Compatibility facade for canonical manual-opening jobs. */
public final class AirlockManualOpening {
    private AirlockManualOpening() {
    }

    public static boolean interact(Player player, BlockPos pos) {
        return AirlockManualOpeningService.interact(player, pos);
    }

    public static void advance(Player player) {
        AirlockManualOpeningService.advance(player);
    }
}
