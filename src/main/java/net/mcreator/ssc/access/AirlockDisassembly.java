package net.mcreator.ssc.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.block.airlock.AirlockDisassemblyService;

/** Compatibility facade for airlock disassembly. */
public final class AirlockDisassembly {
    private AirlockDisassembly() {
    }

    public static boolean interact(Player player, BlockPos pos) {
        return AirlockDisassemblyService.interact(player, pos);
    }
}
