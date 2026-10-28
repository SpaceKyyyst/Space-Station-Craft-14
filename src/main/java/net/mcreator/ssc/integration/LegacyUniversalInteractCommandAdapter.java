package net.mcreator.ssc.integration;

import net.minecraft.server.level.ServerPlayer;
import net.ssc14.content.equipment.ItemInteractionService;

/** Compatibility boundary for the ssc14_interact command procedure. */
public final class LegacyUniversalInteractCommandAdapter {
    private LegacyUniversalInteractCommandAdapter() {
    }

    public static void execute(ServerPlayer player, int slotIndex) {
        ItemInteractionService.execute(player, slotIndex);
    }
}
