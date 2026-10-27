package net.ssc14.integration.neoforge.server;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.ssc14.common.Ssc14Identity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Owns delayed work for each server lifetime and executes it only on its tick thread. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class ServerWorkScheduler {
    private static final int MAX_TASKS_PER_TICK = 512;
    private static final Logger LOGGER = LogManager.getLogger("net.mcreator.ssc.Ssc14Mod");
    private static final Map<MinecraftServer, Session> QUEUES = new IdentityHashMap<>();
    private static final Set<MinecraftServer> STOPPED =
            Collections.newSetFromMap(new WeakHashMap<>());

    private static final class Session {
        private final TickTaskQueue queue = new TickTaskQueue(MAX_TASKS_PER_TICK,
                exception -> LOGGER.atError().withThrowable(exception)
                        .log("Scheduled server task failed"));
        private volatile boolean active = true;
    }

    private ServerWorkScheduler() { }

    @SubscribeEvent
    public static synchronized void started(ServerStartedEvent event) {
        MinecraftServer owner = event.getServer();
        requireTickThread(owner);
        if (!STOPPED.contains(owner) && !owner.isStopped()) {
            QUEUES.computeIfAbsent(owner, ignored -> new Session());
        }
    }

    @SubscribeEvent
    public static synchronized void stopped(ServerStoppedEvent event) {
        MinecraftServer owner = event.getServer();
        requireTickThread(owner);
        Session session = QUEUES.remove(owner);
        if (session != null) session.active = false;
        STOPPED.add(owner);
    }

    public static synchronized ServerTaskSubmission submit(MinecraftServer owner, int delay,
            Runnable action) {
        validateSubmission(delay, action);
        if (owner == null) return ServerTaskSubmission.INVALID_OWNER;
        if (STOPPED.contains(owner) || owner.isStopped()) return ServerTaskSubmission.STOPPED;
        Session session = QUEUES.get(owner);
        if (session == null) return ServerTaskSubmission.UNAVAILABLE;
        return session.queue.submit(delay, () -> {
            if (session.active) action.run();
        }) ? ServerTaskSubmission.ACCEPTED : ServerTaskSubmission.FULL;
    }

    public static void schedule(ServerLevel owner, int delayTicks, Runnable action) {
        validateSubmission(delayTicks, action);
        submit(owner == null ? null : owner.getServer(), delayTicks, action).orThrow();
    }

    public static void tick(MinecraftServer owner) {
        requireTickThread(owner);
        Session session;
        synchronized (ServerWorkScheduler.class) {
            session = QUEUES.get(owner);
        }
        if (session != null && session.active && !owner.isStopped()) {
            session.queue.tick(Integer.toUnsignedLong(owner.getTickCount()));
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        tick(event.getServer());
    }

    public static void validateSubmission(int delay, Runnable action) {
        if (delay < 0) throw new IllegalArgumentException("Delay must be non-negative");
        Objects.requireNonNull(action, "Server task action");
    }

    private static void requireTickThread(MinecraftServer owner) {
        Objects.requireNonNull(owner, "Server task owner");
        if (!owner.isSameThread()) {
            throw new IllegalStateException(
                    "Server task lifecycle and ticks require the owning server thread");
        }
    }
}
