package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** @deprecated Use the canonical interaction command registration. */
@Deprecated(forRemoval = false)
public final class Ssc14interactCommand {
    private Ssc14interactCommand() { }
    public static void registerCommand(RegisterCommandsEvent event) {
        net.ssc14.content.command.Ssc14interactCommand.registerCommand(event);
    }
}
