package net.mcreator.ssc.math;

import java.util.stream.IntStream;

/** Pure layout for the vertical airlock structure and its configurable width. */
public record AirlockMultiblockLayout(int widthBlocks) {
    public AirlockMultiblockLayout {
        if (widthBlocks < 1 || widthBlocks > 3)
            throw new IllegalArgumentException("Airlock width must be 1..3 blocks");
    }

    public int heightBlocks() { return 3; }

    /** Horizontal part offsets, centered around the controller block. */
    public int[] horizontalOffsets() {
        int left = -(widthBlocks - 1) / 2;
        return IntStream.range(0, widthBlocks).map(i -> left + i).toArray();
    }

    public int cellCount() { return widthBlocks * heightBlocks(); }
}
