package net.mcreator.ssc.scheduling;

import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** @deprecated Use {@link ServerWorkScheduler}. */
@Deprecated(forRemoval = false)
public final class ServerTaskScheduler {
    private ServerTaskScheduler() { }

    public static void started(ServerStartedEvent event) {
        ServerWorkScheduler.started(event);
    }

    public static void stopped(ServerStoppedEvent event) {
        ServerWorkScheduler.stopped(event);
    }

    public static SubmissionResult submit(MinecraftServer owner, int delay, Runnable action) {
        return SubmissionResult.valueOf(
                ServerWorkScheduler.submit(owner, delay, action).name());
    }

    public static void tick(MinecraftServer owner) {
        ServerWorkScheduler.tick(owner);
    }

    public static void validateSubmission(int delay, Runnable action) {
        ServerWorkScheduler.validateSubmission(delay, action);
    }
}
