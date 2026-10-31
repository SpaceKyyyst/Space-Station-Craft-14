package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.block.airlock.AirlockWeldingService;

/** Compatibility facade for canonical welding jobs. */
@Deprecated(forRemoval = false)
public final class AirlockWelding {
    private AirlockWelding() {
    }

    public static boolean interact(Player player, BlockPos pos) {
        return AirlockWeldingService.interact(player, pos);
    }

    public static void advance(Player player) {
        AirlockWeldingService.advance(player);
    }
}
