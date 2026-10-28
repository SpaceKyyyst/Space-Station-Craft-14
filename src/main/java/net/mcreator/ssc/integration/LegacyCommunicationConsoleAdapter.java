package net.mcreator.ssc.integration;

import net.mcreator.ssc.procedures.CommConsBLUEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsGREENcodeProcedure;
import net.mcreator.ssc.procedures.CommConsPURPLEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsREDcodeProcedure;
import net.mcreator.ssc.procedures.CommConsYELLOWcodeProcedure;
import net.minecraft.world.level.Level;

/** Compatibility boundary for communication-console alert procedures. */
public final class LegacyCommunicationConsoleAdapter {
    private LegacyCommunicationConsoleAdapter() {
    }

    public static void trigger(Level level, int buttonId) {
        switch (buttonId) {
            case 0 -> CommConsREDcodeProcedure.execute(level);
            case 1 -> CommConsBLUEcodeProcedure.execute(level);
            case 2 -> CommConsPURPLEcodeProcedure.execute(level);
            case 3 -> CommConsYELLOWcodeProcedure.execute(level);
            case 4 -> CommConsGREENcodeProcedure.execute(level);
            default -> { }
        }
    }
}
