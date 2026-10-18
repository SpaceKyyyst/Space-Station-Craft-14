package net.mcreator.ssc.math;

/** Pure geometry for two telescopic leaves, measured in model pixels. */
public final class AirlockMotion {
    public static final int PIXELS_PER_BLOCK = 16;
    public static final int HEIGHT = 3 * PIXELS_PER_BLOCK;
    public static final int BELOW_FLOOR = PIXELS_PER_BLOCK;
    public static final float FRAME_WIDTH = 1;
    public static final float RETRACTED_LEAF_WIDTH = 0.5f;

    private AirlockMotion() { }

    public record Pose(float leftOffset, float rightOffset, float leafScale) { }

    public static float leafWidth(int widthBlocks) {
        if (widthBlocks != 1) throw new IllegalArgumentException("Airlock width is fixed at one block");
        return widthBlocks * PIXELS_PER_BLOCK / 2f - FRAME_WIDTH;
    }

    /** Openness is authoritative progress: 0 closed, 1 open; reversal uses the same curve. */
    public static Pose pose(int widthBlocks, float openness) {
        if (!Float.isFinite(openness)) throw new IllegalArgumentException("Airlock openness must be finite");
        float leafWidth = leafWidth(widthBlocks);
        float progress = Math.clamp(openness, 0f, 1f);
        float eased = progress * progress * (3f - 2f * progress);
        float displacement = (leafWidth - RETRACTED_LEAF_WIDTH) * eased;
        return new Pose(-displacement, displacement, (leafWidth - displacement) / leafWidth);
    }
}
