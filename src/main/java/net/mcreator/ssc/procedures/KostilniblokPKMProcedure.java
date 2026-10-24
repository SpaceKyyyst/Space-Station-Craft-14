package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.block.debug.FailureSentinelInteractionService;

/** @deprecated Failure sentinel blocks delegate directly to the canonical interaction service. */
@Deprecated(forRemoval = false)
public final class KostilniblokPKMProcedure {
    private KostilniblokPKMProcedure() { }

    public static void execute(Entity entity) {
        if (entity instanceof Player player) {
            FailureSentinelInteractionService.disconnect(player);
        }
    }
}
