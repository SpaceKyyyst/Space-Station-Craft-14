package net.ssc14.core.energy;

/** One device's shared transfer limits for one level tick, based on its initial stored energy. */
public final class EnergyTickBudget {
    private long outputRemaining;
    private long inputRemaining;

    public EnergyTickBudget(long stored, long capacity, long outputLimit, long inputLimit) {
        long safeCapacity = Math.max(0, capacity);
        long safeStored = Math.clamp(stored, 0, safeCapacity);
        outputRemaining = Math.min(safeStored, Math.max(0, outputLimit));
        inputRemaining = Math.min(safeCapacity - safeStored, Math.max(0, inputLimit));
    }

    public long availableOutput() {
        return outputRemaining;
    }

    public long remainingInput() {
        return inputRemaining;
    }

    public long takeOutput(long requested) {
        long accepted = Math.min(outputRemaining, Math.max(0, requested));
        outputRemaining -= accepted;
        return accepted;
    }

    public long acceptInput(long requested) {
        long accepted = Math.min(inputRemaining, Math.max(0, requested));
        inputRemaining -= accepted;
        return accepted;
    }
}
