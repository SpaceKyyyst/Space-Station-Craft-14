package net.mcreator.ssc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.ssc.scheduling.TickTaskQueue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TickTaskQueueTest {
    @Test void capacityIncludesDelayedTasksAndIsReleasedAfterFailure() {
        var queue = new TickTaskQueue(2, 2, error -> { });
        assertTrue(queue.submit(5, () -> { throw new IllegalStateException("fixture"); }));
        assertTrue(queue.submit(5, () -> { }));
        assertFalse(queue.submit(0, () -> fail("Rejected task ran")));
        assertEquals(0, queue.tick(0));
        assertFalse(queue.submit(0, () -> fail("Scheduled tasks did not count toward capacity")));
        assertEquals(2, queue.tick(5));
        assertTrue(queue.submit(0, () -> { }));
    }

    @Test void concurrentProducersCannotExceedCapacity() throws InterruptedException {
        var accepted = new java.util.concurrent.atomic.AtomicInteger();
        var queue = new TickTaskQueue(2, 17, error -> fail(error));
        var workers = new ArrayList<Thread>();
        for (int i = 0; i < 8; i++) {
            var worker = new Thread(() -> {
                for (int attempt = 0; attempt < 50; attempt++) {
                    if (queue.submit(0, () -> { })) accepted.incrementAndGet();
                }
            });
            workers.add(worker);
            worker.start();
        }
        for (var worker : workers) worker.join();
        assertEquals(17, accepted.get());
        assertEquals(2, queue.tick(0));
        assertTrue(queue.submit(0, () -> { }));
        assertTrue(queue.submit(0, () -> { }));
        assertFalse(queue.submit(0, () -> { }));
    }

    @Test void preservesDelayAndFifoOrderWithoutExceedingBudget() {
        var calls = new ArrayList<Integer>();
        var queue = new TickTaskQueue(2, error -> fail(error));
        queue.submit(2, () -> calls.add(1));
        queue.submit(2, () -> calls.add(2));
        queue.submit(0, () -> calls.add(3));
        assertEquals(0, queue.tick(10));
        assertEquals(1, queue.tick(11));
        assertEquals(2, queue.tick(12));
        assertEquals(List.of(3, 1, 2), calls);
    }

    @Test void workerSubmitsButOnlyTickThreadExecutes() throws InterruptedException {
        var ranOn = new AtomicReference<Thread>();
        var queue = new TickTaskQueue(2, error -> fail(error));
        var worker = new Thread(() -> queue.submit(0, () -> ranOn.set(Thread.currentThread())));
        worker.start(); worker.join();
        assertNull(ranOn.get());
        queue.tick(1);
        assertSame(Thread.currentThread(), ranOn.get());
    }

    @Test void failingAndRecursiveTasksDoNotBlockOthers() {
        var failures = new ArrayList<RuntimeException>();
        var calls = new ArrayList<Integer>();
        var queue = new TickTaskQueue(2, failures::add);
        queue.submit(0, () -> { throw new IllegalStateException("fixture"); });
        queue.submit(0, () -> { calls.add(1); queue.submit(0, () -> calls.add(2)); });
        assertEquals(2, queue.tick(1));
        assertEquals(List.of(1), calls);
        assertEquals(1, failures.size());
        assertEquals(1, queue.tick(2));
        assertEquals(List.of(1, 2), calls);
    }
}
