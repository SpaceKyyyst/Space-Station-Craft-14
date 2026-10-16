package net.mcreator.ssc.scheduling;

import java.util.Map;
import java.util.WeakHashMap;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Explicit server ownership prevents delayed work from leaking into the next loaded world. */
@EventBusSubscriber(modid = "ssc_14")
public final class ServerTaskScheduler {
    private static final int MAX_TASKS_PER_TICK = 512;
    private static final Map<MinecraftServer, TickTaskQueue> QUEUES = new WeakHashMap<>();
    private ServerTaskScheduler() { }

    @SubscribeEvent
    public static synchronized void started(ServerStartedEvent event) {
        QUEUES.put(event.getServer(), new TickTaskQueue(MAX_TASKS_PER_TICK,
                exception -> Ssc14Mod.LOGGER.error("Scheduled server task failed", exception)));
    }

    @SubscribeEvent
    public static synchronized void stopped(ServerStoppedEvent event) { QUEUES.remove(event.getServer()); }

    /** May be called from a worker; the action itself always runs on the owning server's tick. */
    public static synchronized boolean submit(MinecraftServer server, int delay, Runnable action) {
        var queue = QUEUES.get(server);
        if (queue == null) return false;
        return queue.submit(delay, action);
    }

    public static void tick(MinecraftServer server) {
        TickTaskQueue queue;
        synchronized (ServerTaskScheduler.class) { queue = QUEUES.get(server); }
        if (queue != null) queue.tick(Integer.toUnsignedLong(server.getTickCount()));
    }
}
