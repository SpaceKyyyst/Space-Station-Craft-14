package net.ssc14.integration.neoforge.server;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.block.ShutterController;
import net.mcreator.ssc.procedures.Button_CLICK_Procedure;
import net.mcreator.ssc.procedures.C4explorePRProcedure;
import net.mcreator.ssc.procedures.CommunicationServers_TIC_PR_Procedure;
import net.ssc14.content.commerce.VendingMachineRuntimeService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;
import static org.junit.jupiter.api.Assertions.*;

class ServerWorkSchedulerTest {
    private final List<MinecraftServer> owners = new ArrayList<>();
    private final List<LogEvent> logs = new ArrayList<>();
    private Logger logger;
    private AbstractAppender appender;
    private boolean originalAdditivity;

    @BeforeEach
    void captureSchedulerDiagnostics() {
        // The FML harness's file-layout %xEx tries to resolve mod classes in the system loader.
        // Capture real log events directly so expected failures also verify the diagnostics contract.
        logger = (Logger) Ssc14Mod.LOGGER;
        originalAdditivity = logger.isAdditive();
        appender = new AbstractAppender("SchedulerDiagnostics", null, null, true, Property.EMPTY_ARRAY) {
            @Override public void append(LogEvent event) { logs.add(event.toImmutable()); }
        };
        appender.start();
        logger.addAppender(appender);
        logger.setAdditive(false);
    }

    @AfterEach
    void stopOwners() {
        try {
            owners.forEach(owner -> ServerWorkScheduler.stopped(new ServerStoppedEvent(owner)));
        } finally {
            logger.removeAppender(appender);
            logger.setAdditive(originalAdditivity);
            appender.stop();
        }
    }

    @Test
    void workerSubmissionThroughFacadeRunsOnlyOnOwningTickThread() throws Exception {
        var owner = server(true);
        var other = server(true);
        var result = new AtomicReference<Object>();
        var ranOn = new AtomicReference<Thread>();
        var worker = new Thread(() -> result.set(Ssc14Mod.queueServerWork(owner, 0,
                () -> ranOn.set(Thread.currentThread()))));
        worker.start();
        worker.join();
        assertEquals("ACCEPTED", result.get().toString());
        assertNull(ranOn.get());
        ServerWorkScheduler.tick(other);
        assertNull(ranOn.get(), "Another server must not execute this owner's task");
        ServerWorkScheduler.tick(owner);
        assertSame(Thread.currentThread(), ranOn.get());
    }

    @Test
    void workerCannotExecuteAnOwnersTick() throws Exception {
        var owner = server(true);
        var ranOn = new AtomicReference<Thread>();
        ServerWorkScheduler.submit(owner, 0, () -> ranOn.set(Thread.currentThread()));
        var failure = new AtomicReference<Throwable>();
        var worker = new Thread(() -> {
            try {
                ServerWorkScheduler.tick(owner);
            } catch (Throwable error) {
                failure.set(error);
            }
        });
        worker.start();
        worker.join();
        assertInstanceOf(IllegalStateException.class, failure.get());
        assertNull(ranOn.get());
        ServerWorkScheduler.tick(owner);
        assertSame(Thread.currentThread(), ranOn.get());
    }

    @Test
    void unavailableStoppedAndInvalidOwnersAreDistinct() throws Exception {
        var owner = server(false);
        assertEquals("UNAVAILABLE", Ssc14Mod.queueServerWork(owner, 0, () -> fail()).toString());
        ServerWorkScheduler.started(new ServerStartedEvent(owner));
        ServerWorkScheduler.stopped(new ServerStoppedEvent(owner));
        assertEquals("STOPPED", Ssc14Mod.queueServerWork(owner, 0, () -> fail()).toString());
        assertEquals("INVALID_OWNER", Ssc14Mod.queueServerWork((MinecraftServer) null, 0, () -> fail()).toString());
        assertEquals("INVALID_OWNER", Ssc14Mod.queueServerWork((ServerLevel) null, 0, () -> fail()).toString());
    }

    @Test
    void clientAndNonServerAccessorsCannotBorrowAnActiveServer() throws Exception {
        server(true);
        assertEquals("CLIENT_SIDE", Ssc14Mod.queueServerWork(accessor(true), 0, () -> fail()).toString());
        assertEquals("INVALID_OWNER", Ssc14Mod.queueServerWork(accessor(false), 0, () -> fail()).toString());
    }

    @Test
    void clientGameplayAndUiEntrypointsReturnBeforeWorldAccess() throws Exception {
        server(true);
        var client = accessor(true);
        assertDoesNotThrow(() -> {
            Button_CLICK_Procedure.execute(client, 0, 0, 0);
            C4explorePRProcedure.execute(client, 0, 0, 0);
            CommunicationServers_TIC_PR_Procedure.execute(client, 0, 0, 0, null);
            VendingMachineRuntimeService.vend(client, BlockPos.ZERO, 0);
            ShutterController.setOpen(client, BlockPos.ZERO, true);
        });
    }

    @Test
    void serverLevelUsesItsPassedServer() throws Exception {
        var owner = server(true);
        var other = server(true);
        var level = allocate(ServerLevel.class);
        setField(ServerLevel.class, level, "server", owner);
        var calls = new ArrayList<String>();
        assertEquals("ACCEPTED", Ssc14Mod.queueServerWork(level, 0, () -> calls.add("owner")).toString());
        ServerWorkScheduler.tick(other);
        assertTrue(calls.isEmpty());
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("owner"), calls);
    }

    @Test
    void capacityRejectsExplicitlyAndExecutionReleasesSpace() throws Exception {
        var owner = server(true);
        // Production capacity is bounded; fill through the real public admission boundary.
        int accepted = 0;
        Object result;
        do {
            result = Ssc14Mod.queueServerWork(owner, 0, () -> { });
            if (result.toString().equals("ACCEPTED")) accepted++;
            assertTrue(accepted <= 100_000, "Admission must be bounded");
        } while (result.toString().equals("ACCEPTED"));
        assertTrue(accepted > 0);
        assertEquals("FULL", result.toString());
        ServerWorkScheduler.tick(owner);
        assertEquals("ACCEPTED", Ssc14Mod.queueServerWork(owner, 0, () -> { }).toString());
    }

    @Test
    void stoppedQueueCannotRunOrLeakIntoRestart() throws Exception {
        var first = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(first, 0, () -> calls.add("old"));
        ServerWorkScheduler.stopped(new ServerStoppedEvent(first));
        var replacement = server(true);
        Ssc14Mod.queueServerWork(replacement, 0, () -> calls.add("new"));
        ServerWorkScheduler.tick(first);
        ServerWorkScheduler.tick(replacement);
        assertEquals(List.of("new"), calls);
        assertEquals("STOPPED", Ssc14Mod.queueServerWork(first, 0, () -> fail()).toString());
    }

    @Test
    void vanillaStoppedFlagRejectsBeforeTheLifecycleEventArrives() throws Exception {
        var owner = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(owner, 0, () -> calls.add("stale"));
        setField(MinecraftServer.class, owner, "stopped", true);
        assertEquals("STOPPED", Ssc14Mod.queueServerWork(owner, 0, () -> fail()).toString());
        ServerWorkScheduler.tick(owner);
        assertTrue(calls.isEmpty());
    }

    @Test
    void failingTaskDoesNotPreventTheNextTask() throws Exception {
        var owner = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(owner, 0, () -> { throw new IllegalStateException("Expected task fixture failure"); });
        Ssc14Mod.queueServerWork(owner, 0, () -> calls.add("next"));
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("next"), calls);
        assertEquals(1, logs.size());
        assertEquals(Level.ERROR, logs.getFirst().getLevel());
        assertInstanceOf(IllegalStateException.class, logs.getFirst().getThrown());
    }

    @Test
    void stopInsideAnActionCancelsTheRestOfThatTick() throws Exception {
        var owner = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(owner, 0, () -> {
            calls.add("stop");
            ServerWorkScheduler.stopped(new ServerStoppedEvent(owner));
        });
        Ssc14Mod.queueServerWork(owner, 0, () -> calls.add("after stop"));
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("stop"), calls);
    }

    @Test
    void duplicateStartedDoesNotDiscardAcceptedWorkOrReviveStoppedOwner() throws Exception {
        var owner = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(owner, 0, () -> calls.add("kept"));
        ServerWorkScheduler.started(new ServerStartedEvent(owner));
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("kept"), calls);
        ServerWorkScheduler.stopped(new ServerStoppedEvent(owner));
        ServerWorkScheduler.started(new ServerStartedEvent(owner));
        assertEquals("STOPPED", Ssc14Mod.queueServerWork(owner, 0, () -> fail()).toString());
    }

    @Test
    void sameDueTickIsFifoAndRecursiveSubmissionWaitsForNextTick() throws Exception {
        var owner = server(true);
        var calls = new ArrayList<String>();
        Ssc14Mod.queueServerWork(owner, 2, () -> {
            calls.add("first");
            Ssc14Mod.queueServerWork(owner, 0, () -> calls.add("recursive"));
        });
        Ssc14Mod.queueServerWork(owner, 2, () -> calls.add("second"));
        ServerWorkScheduler.tick(owner);
        setField(MinecraftServer.class, owner, "tickCount", 1);
        ServerWorkScheduler.tick(owner);
        assertTrue(calls.isEmpty());
        setField(MinecraftServer.class, owner, "tickCount", 2);
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("first", "second"), calls);
        setField(MinecraftServer.class, owner, "tickCount", 3);
        ServerWorkScheduler.tick(owner);
        assertEquals(List.of("first", "second", "recursive"), calls);
    }

    @Test
    void invalidSubmissionArgumentsThrowBeforeAdmission() throws Exception {
        var owner = server(true);
        assertThrows(IllegalArgumentException.class, () -> Ssc14Mod.queueServerWork(owner, -1, () -> fail()));
        assertThrows(NullPointerException.class, () -> Ssc14Mod.queueServerWork(owner, 0, null));
        assertThrows(RejectedExecutionException.class,
                () -> Ssc14Mod.queueServerWork((MinecraftServer) null, 0, () -> fail()).orThrow());
        assertEquals(1, logs.size());
        assertEquals(Level.WARN, logs.getFirst().getLevel());
        assertTrue(logs.getFirst().getMessage().getFormattedMessage().contains("INVALID_OWNER"));
        assertDoesNotThrow(() -> Ssc14Mod.queueServerWork(owner, 0, () -> { }).orThrow());
    }

    private MinecraftServer server(boolean started) throws Exception {
        var owner = allocate(DedicatedServer.class);
        setField(MinecraftServer.class, owner, "serverThread", Thread.currentThread());
        owners.add(owner);
        if (started) ServerWorkScheduler.started(new ServerStartedEvent(owner));
        return owner;
    }

    // No world IO is needed for this adapter. Real vanilla methods read these initialized fields;
    // avoiding the server constructor avoids starting registries, world storage and networking here.
    private static <T> T allocate(Class<T> type) throws Exception {
        var field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return type.cast(((Unsafe) field.get(null)).allocateInstance(type));
    }

    private static void setField(Class<?> type, Object instance, String name, Object value) throws Exception {
        var field = type.getDeclaredField(name);
        field.setAccessible(true);
        field.set(instance, value);
    }

    private static LevelAccessor accessor(boolean client) {
        return (LevelAccessor) Proxy.newProxyInstance(LevelAccessor.class.getClassLoader(),
                new Class<?>[] {LevelAccessor.class}, (proxy, method, args) -> {
                    if (method.getName().equals("isClientSide")) return client;
                    throw new AssertionError("Side rejection must not access " + method.getName());
                });
    }
}
