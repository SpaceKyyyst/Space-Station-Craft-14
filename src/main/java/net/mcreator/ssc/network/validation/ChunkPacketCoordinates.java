package net.mcreator.ssc.network.validation;

/** Existing decal wire layout: X occupies the high word, Z the low word. */
public final class ChunkPacketCoordinates {
    private ChunkPacketCoordinates() {
    }

    public static long pack(int x, int z) {
        return ((long) x << Integer.SIZE) | Integer.toUnsignedLong(z);
    }

    public static int x(long packed) {
        return (int) (packed >> Integer.SIZE);
    }

    public static int z(long packed) {
        return (int) packed;
    }
}
