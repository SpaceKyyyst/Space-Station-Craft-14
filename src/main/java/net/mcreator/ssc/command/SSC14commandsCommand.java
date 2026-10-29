package net.mcreator.ssc.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** @deprecated Use the canonical SSC14 command registration. */
@Deprecated(forRemoval = false)
public final class SSC14commandsCommand {
    private SSC14commandsCommand() {
    }

    public static void registerCommand(RegisterCommandsEvent event) {
        net.ssc14.content.command.SSC14commandsCommand.registerCommand(event);
    }
}
