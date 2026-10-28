package net.mcreator.ssc.integration;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.mcreator.ssc.ModReagents;

/** Compatibility boundary for the still-legacy reaction executor. */
public final class LegacyReactionRuntime {
    private LegacyReactionRuntime() {
    }

    public static void process(ItemStack stack, ServerPlayer player) {
        ModReagents.processReactions(stack, player);
    }
}
