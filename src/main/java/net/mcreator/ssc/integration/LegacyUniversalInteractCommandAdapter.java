package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.UniversalInteractCommandProcedure;
import net.minecraft.server.level.ServerPlayer;

/** Compatibility boundary for the ssc14_interact command procedure. */
public final class LegacyUniversalInteractCommandAdapter {
    private LegacyUniversalInteractCommandAdapter() {
    }

    public static void execute(ServerPlayer player, int slotIndex) {
        UniversalInteractCommandProcedure.execute(player, slotIndex);
    }
}
