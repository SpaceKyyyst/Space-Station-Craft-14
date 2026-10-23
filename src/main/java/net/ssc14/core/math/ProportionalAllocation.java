package net.ssc14.core.math;

import java.math.BigInteger;

/** Integer allocation preserving the exact budget and each recipient's limit. */
public final class ProportionalAllocation {
    private ProportionalAllocation() { }

    public static long[] allocate(long budget, long[] limits) {
        if (budget < 0) throw new IllegalArgumentException("Negative budget");
        BigInteger total = BigInteger.ZERO;
        for (long limit : limits) {
            if (limit < 0) throw new IllegalArgumentException("Negative limit");
            total = total.add(BigInteger.valueOf(limit));
        }
        long[] result = new long[limits.length];
        if (total.signum() == 0) return result;
        BigInteger available = BigInteger.valueOf(budget).min(total);
        BigInteger cumulative = BigInteger.ZERO;
        long assigned = 0;
        for (int index = 0; index < limits.length; index++) {
            cumulative = cumulative.add(BigInteger.valueOf(limits[index]));
            long target = cumulative.multiply(available).divide(total).longValueExact();
            result[index] = target - assigned;
            assigned = target;
        }
        return result;
    }
}
