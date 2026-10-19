package net.mcreator.ssc.scheduling;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Owns one queue per server lifetime. Stop cancels pending work; the same server instance cannot restart.
 * Lifecycle events and ticks must run on that server's thread, otherwise they throw IllegalStateException.
 */
@EventBusSubscriber(modid = "ssc_14")
public final class ServerTaskScheduler {
    private static final int MAX_TASKS_PER_TICK = 512;
    private static final Map<MinecraftServer, Session> QUEUES = new IdentityHashMap<>();
    private static final Set<MinecraftServer> STOPPED = Collections.newSetFromMap(new WeakHashMap<>());

    private static final class Session {
        private final TickTaskQueue queue = new TickTaskQueue(MAX_TASKS_PER_TICK,
                exception -> Ssc14Mod.LOGGER.error("Scheduled server task failed", exception));
        private volatile boolean active = true;
    }

    private ServerTaskScheduler() { }

    @SubscribeEvent
    public static synchronized void started(ServerStartedEvent event) {
        var owner = event.getServer();
        requireTickThread(owner);
        if (!STOPPED.contains(owner) && !owner.isStopped()) QUEUES.computeIfAbsent(owner, ignored -> new Session());
    }

    @SubscribeEvent
    public static synchronized void stopped(ServerStoppedEvent event) {
        var owner = event.getServer();
        requireTickThread(owner);
        var session = QUEUES.remove(owner);
        if (session != null) session.active = false;
        STOPPED.add(owner);
    }

    /**
     * May be called from a worker; the action itself always runs on the owning server's tick.
     * Negative delay throws IllegalArgumentException and a null action throws NullPointerException,
     * even when the owner is invalid or unavailable. All owner/admission refusals return a result.
     */
    public static synchronized SubmissionResult submit(MinecraftServer owner, int delay, Runnable action) {
        validateSubmission(delay, action);
        if (owner == null) return SubmissionResult.INVALID_OWNER;
        if (STOPPED.contains(owner) || owner.isStopped()) return SubmissionResult.STOPPED;
        var session = QUEUES.get(owner);
        if (session == null) return SubmissionResult.UNAVAILABLE;
        return session.queue.submit(delay, () -> {
            // A stop during an earlier action also cancels the remaining ready actions this tick.
            if (session.active) action.run();
        }) ? SubmissionResult.ACCEPTED : SubmissionResult.FULL;
    }

    public static void tick(MinecraftServer owner) {
        requireTickThread(owner);
        Session session;
        synchronized (ServerTaskScheduler.class) { session = QUEUES.get(owner); }
        // Do not hold the admission/lifecycle lock while executing arbitrary gameplay callbacks.
        if (session != null && session.active && !owner.isStopped()) {
            session.queue.tick(Integer.toUnsignedLong(owner.getTickCount()));
        }
    }

    public static void validateSubmission(int delay, Runnable action) {
        if (delay < 0) throw new IllegalArgumentException("Delay must be non-negative");
        Objects.requireNonNull(action, "Server task action");
    }

    private static void requireTickThread(MinecraftServer owner) {
        Objects.requireNonNull(owner, "Server task owner");
        if (!owner.isSameThread()) throw new IllegalStateException("Server task lifecycle and ticks require the owning server thread");
    }
}
