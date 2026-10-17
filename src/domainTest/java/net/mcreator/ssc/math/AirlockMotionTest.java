package net.mcreator.ssc.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AirlockMotionTest {
    @Test
    void leavesStayInsideFrameAndNeverDisappearAtEveryWidth() {
        for (int width = 1; width <= 3; width++) {
            float leaf = AirlockMotion.leafWidth(width);
            float previousOffset = 0;
            for (int step = 0; step <= 100; step++) {
                var pose = AirlockMotion.pose(width, step / 100f);
                assertEquals(-pose.leftOffset(), pose.rightOffset());
                assertTrue(pose.rightOffset() >= previousOffset);
                assertEquals(leaf, pose.rightOffset() + leaf * pose.leafScale(), 0.00001f);
                assertTrue(pose.leafScale() > 0);
                previousOffset = pose.rightOffset();
            }
            assertEquals(1f, AirlockMotion.pose(width, 0).leafScale());
            assertEquals(AirlockMotion.RETRACTED_LEAF_WIDTH,
                    leaf * AirlockMotion.pose(width, 1).leafScale(), 0.00001f);
        }
    }

    @Test
    void validatesInputAndClampsNetworkOvershoot() {
        assertThrows(IllegalArgumentException.class, () -> AirlockMotion.pose(0, 0));
        assertThrows(IllegalArgumentException.class, () -> AirlockMotion.pose(4, 0));
        assertThrows(IllegalArgumentException.class, () -> AirlockMotion.pose(1, Float.NaN));
        assertThrows(IllegalArgumentException.class, () -> AirlockMotion.pose(1, Float.POSITIVE_INFINITY));
        assertEquals(AirlockMotion.pose(1, 0), AirlockMotion.pose(1, -1));
        assertEquals(AirlockMotion.pose(1, 1), AirlockMotion.pose(1, 2));
    }
}
