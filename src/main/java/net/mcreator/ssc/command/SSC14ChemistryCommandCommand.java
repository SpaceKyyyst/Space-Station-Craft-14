package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** @deprecated Use the canonical chemistry command registration. */
@Deprecated(forRemoval = false)
public final class SSC14ChemistryCommandCommand {
    private SSC14ChemistryCommandCommand() { }
    public static void registerCommand(RegisterCommandsEvent event) {
        net.ssc14.content.command.SSC14ChemistryCommandCommand.registerCommand(event);
    }
}
