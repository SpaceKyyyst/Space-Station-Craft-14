package net.mcreator.ssc.integration;

import com.mojang.brigadier.context.CommandContext;
import net.mcreator.ssc.procedures.TpSSC14mineOveworldprProcedure;
import net.mcreator.ssc.procedures.TpSSC14planetplantprProcedure;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.Level;
import net.ssc14.content.medical.MedicalResetService;
import net.ssc14.content.atmosphere.runtime.SetGasCommandService;
import net.ssc14.content.atmosphere.runtime.SetGasTemperatureCommandService;
import net.ssc14.content.world.DimensionTeleportService;

/** Transitional boundary for generated administration commands. */
public final class LegacyAdministrationCommandAdapter {
    private LegacyAdministrationCommandAdapter() {
    }

    public static void teleportSpace(CommandContext<CommandSourceStack> context) {
        DimensionTeleportService.teleportToSpace(context);
    }

    public static void teleportPlanet(CommandContext<CommandSourceStack> context) {
        TpSSC14planetplantprProcedure.execute(context);
    }

    public static void teleportOverworld(CommandContext<CommandSourceStack> context) {
        TpSSC14mineOveworldprProcedure.execute(context);
    }

    public static void resetHealth(CommandContext<CommandSourceStack> context) {
        MedicalResetService.execute(context);
    }

    public static void setGas(Level level, CommandContext<CommandSourceStack> context) {
        SetGasCommandService.execute(level, context);
    }

    public static void setTemperature(Level level, CommandContext<CommandSourceStack> context) {
        SetGasTemperatureCommandService.execute(level, context);
    }
}
