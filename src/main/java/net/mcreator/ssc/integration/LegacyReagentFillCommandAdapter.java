package net.mcreator.ssc.integration;

import com.mojang.brigadier.context.CommandContext;
import net.mcreator.ssc.procedures.FillReagentPRProcedure;
import net.minecraft.commands.CommandSourceStack;

/** Compatibility boundary for the legacy reagent-fill command procedure. */
public final class LegacyReagentFillCommandAdapter {
    private LegacyReagentFillCommandAdapter() {
    }

    public static int execute(CommandContext<CommandSourceStack> context) {
        FillReagentPRProcedure.execute(context);
        return 0;
    }
}
