package net.mcreator.ssc.scheduling;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/** Concurrent submissions, single-threaded execution with a bounded number of tasks per tick. */
public final class TickTaskQueue {
    private static final int DEFAULT_CAPACITY = 65_536;
    private record Submission(int delay, Runnable action) { }
    private record Scheduled(long dueTick, long sequence, Runnable action) { }
    private final ConcurrentLinkedQueue<Submission> incoming = new ConcurrentLinkedQueue<>();
    private final PriorityQueue<Scheduled> scheduled = new PriorityQueue<>(
            Comparator.comparingLong(Scheduled::dueTick).thenComparingLong(Scheduled::sequence));
    private final int budget;
    private final int capacity;
    private final AtomicInteger pending = new AtomicInteger();
    private final Consumer<RuntimeException> onFailure;
    private long sequence;

    public TickTaskQueue(int budget, Consumer<RuntimeException> onFailure) {
        this(budget, DEFAULT_CAPACITY, onFailure);
    }

    public TickTaskQueue(int budget, int capacity, Consumer<RuntimeException> onFailure) {
        if (budget < 1) throw new IllegalArgumentException("Task budget must be positive");
        if (capacity < 1) throw new IllegalArgumentException("Task capacity must be positive");
        this.budget = budget;
        this.capacity = capacity;
        this.onFailure = Objects.requireNonNull(onFailure);
    }

    public boolean submit(int delay, Runnable action) {
        if (delay < 0) throw new IllegalArgumentException("Delay must be non-negative");
        Objects.requireNonNull(action);
        int count;
        do {
            count = pending.get();
            if (count >= capacity) return false;
        } while (!pending.compareAndSet(count, count + 1));
        incoming.add(new Submission(delay, action));
        return true;
    }

    public int tick(long currentTick) {
        for (int accepted = 0; accepted < budget; accepted++) {
            var submission = incoming.poll();
            if (submission == null) break;
            scheduled.add(new Scheduled(currentTick + submission.delay, sequence++, submission.action));
        }
        int executed = 0;
        while (executed < budget && !scheduled.isEmpty() && scheduled.peek().dueTick <= currentTick) {
            var task = scheduled.remove();
            pending.decrementAndGet();
            try {
                task.action.run();
            } catch (RuntimeException exception) {
                onFailure.accept(exception);
            }
            executed++;
        }
        return executed;
    }
}
