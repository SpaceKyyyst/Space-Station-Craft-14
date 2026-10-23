package net.mcreator.ssc.math;

/** Legacy entry point for proportional integer allocation. */
public final class ProportionalAllocation {
    private ProportionalAllocation() { }

    /**
     * Delegates to the target Core implementation.
     *
     * @deprecated Use {@link net.ssc14.core.math.ProportionalAllocation#allocate(long, long[])}.
     */
    @Deprecated(forRemoval = false)
    public static long[] allocate(long budget, long[] limits) {
        return net.ssc14.core.math.ProportionalAllocation.allocate(budget, limits);
    }
}
