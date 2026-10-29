package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/** Compatibility facade for canonical server-authoritative wire handling. */
public final class AirlockWireService {
    private AirlockWireService() {
    }

    public static boolean canUsePanel(Player player, BlockPos pos) {
        return net.ssc14.content.block.airlock.AirlockWireService.canUsePanel(player, pos);
    }

    public static void toggle(Player player, BlockPos pos, int wire) {
        net.ssc14.content.block.airlock.AirlockWireService.toggle(player, pos, wire);
    }
}
