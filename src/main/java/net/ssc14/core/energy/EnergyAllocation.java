package net.ssc14.core.energy;

import java.math.BigInteger;

/** Proportional integer distribution with exact conservation, including rounding remainders. */
public final class EnergyAllocation {
    private EnergyAllocation() {
    }

    public static long[] distribute(long available, long[] demand) {
        long[] allocation = new long[demand.length];
        BigInteger total = BigInteger.ZERO;
        for (long requested : demand) {
            total = total.add(BigInteger.valueOf(Math.max(0, requested)));
        }
        if (available <= 0 || total.signum() == 0) {
            return allocation;
        }
        BigInteger supplied = BigInteger.valueOf(available).min(total);
        BigInteger cumulativeDemand = BigInteger.ZERO;
        long previouslyAllocated = 0;
        for (int i = 0; i < demand.length; i++) {
            cumulativeDemand = cumulativeDemand.add(BigInteger.valueOf(Math.max(0, demand[i])));
            long cumulativeAllocation = supplied.multiply(cumulativeDemand).divide(total).longValueExact();
            allocation[i] = cumulativeAllocation - previouslyAllocated;
            previouslyAllocated = cumulativeAllocation;
        }
        return allocation;
    }
}
