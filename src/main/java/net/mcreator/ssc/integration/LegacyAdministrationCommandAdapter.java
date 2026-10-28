package net.mcreator.ssc.integration;

import com.mojang.brigadier.context.CommandContext;
import net.mcreator.ssc.procedures.HelsResetCommandProcedure;
import net.mcreator.ssc.procedures.SSC14AtmosGasTemperatureCommandProcedure;
import net.mcreator.ssc.procedures.SSC14AtmosSetGasCommandProcedure;
import net.mcreator.ssc.procedures.TpSSC14mineOveworldprProcedure;
import net.mcreator.ssc.procedures.TpSSC14planetplantprProcedure;
import net.mcreator.ssc.procedures.TpSSC14spaceprProcedure;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.Level;

/** Transitional boundary for generated administration commands. */
public final class LegacyAdministrationCommandAdapter {
    private LegacyAdministrationCommandAdapter() {
    }

    public static void teleportSpace(CommandContext<CommandSourceStack> context) {
        TpSSC14spaceprProcedure.execute(context);
    }

    public static void teleportPlanet(CommandContext<CommandSourceStack> context) {
        TpSSC14planetplantprProcedure.execute(context);
    }

    public static void teleportOverworld(CommandContext<CommandSourceStack> context) {
        TpSSC14mineOveworldprProcedure.execute(context);
    }

    public static void resetHealth(CommandContext<CommandSourceStack> context) {
        HelsResetCommandProcedure.execute(context);
    }

    public static void setGas(Level level, CommandContext<CommandSourceStack> context) {
        SSC14AtmosSetGasCommandProcedure.execute(level, context);
    }

    public static void setTemperature(Level level, CommandContext<CommandSourceStack> context) {
        SSC14AtmosGasTemperatureCommandProcedure.execute(level, context);
    }
}
