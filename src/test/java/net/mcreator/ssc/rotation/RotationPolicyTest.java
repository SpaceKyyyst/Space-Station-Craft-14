package net.mcreator.ssc.rotation;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RotationPolicyTest {
    @Test
    void horizontalFacingMakesOneClockwiseQuarterTurn() {
        var state = Blocks.FURNACE.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        for (Direction expected : new Direction[] {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH}) {
            state = RotationPolicy.rotateOrientation(state).orElseThrow();
            assertEquals(expected, state.getValue(HorizontalDirectionalBlock.FACING));
        }
    }

    @Test
    void horizontalAxesExchangeWithoutChangingOtherState() {
        var x = Blocks.OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
        var z = RotationPolicy.rotateOrientation(x).orElseThrow();
        assertEquals(Direction.Axis.Z, z.getValue(RotatedPillarBlock.AXIS));
        assertSame(x, RotationPolicy.rotateOrientation(z).orElseThrow());
    }

    @Test
    void sixWayFacingKeepsNonOrientationProperties() {
        var north = Blocks.DISPENSER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH);
        var east = RotationPolicy.rotateOrientation(north).orElseThrow();
        assertSame(north.setValue(DirectionalBlock.FACING, Direction.EAST), east);
    }

    @Test
    void verticalFacingAxisAndMissingPropertyFailClosedWithoutAnException() {
        assertTrue(RotationPolicy.rotateOrientation(Blocks.OAK_LOG.defaultBlockState()).isEmpty());
        for (Direction direction : new Direction[] {Direction.UP, Direction.DOWN}) {
            assertTrue(RotationPolicy.rotateOrientation(Blocks.DISPENSER.defaultBlockState().setValue(DirectionalBlock.FACING, direction)).isEmpty());
        }
        assertTrue(RotationPolicy.rotateOrientation(Blocks.STONE.defaultBlockState()).isEmpty());
    }

    @Test
    void publicPolicyRejectsAnUntaggedStateEvenWithASupportedFacing() {
        assertTrue(RotationPolicy.rotate(Blocks.FURNACE.defaultBlockState()).isEmpty());
    }
}
