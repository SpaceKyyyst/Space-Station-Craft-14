package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Maintains the canonical linkage flags for vertically adjacent shutters. */
public final class ShuttersStateService {
    private ShuttersStateService() { }

    public static void update(Level level, BlockPos pos, BlockState state) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());
        boolean up = isCompatible(state, above);
        boolean down = isCompatible(state, below)
                && (below.getValue(ShuttersBlock.OPENING) || !below.getValue(ShuttersBlock.OPEN));
        if (state.getValue(ShuttersBlock.UP) != up
                || state.getValue(ShuttersBlock.DOWN) != down) {
            level.setBlock(pos, state.setValue(ShuttersBlock.UP, up)
                    .setValue(ShuttersBlock.DOWN, down), 3);
        }
    }

    private static boolean isCompatible(BlockState state, BlockState neighbor) {
        return neighbor.getBlock() instanceof ShuttersBlock
                && sameAxis(state.getValue(ShuttersBlock.FACING),
                        neighbor.getValue(ShuttersBlock.FACING));
    }

    private static boolean sameAxis(Direction first, Direction second) {
        return first.getAxis() == second.getAxis();
    }
}
