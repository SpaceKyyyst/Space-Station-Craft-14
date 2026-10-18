package net.mcreator.ssc.access;

import net.mcreator.ssc.math.AirlockMultiblockLayout;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;

/** Server-side placement checks for an airlock footprint. No blocks are mutated here. */
public final class AirlockMultiblockPlacement {
    private AirlockMultiblockPlacement() { }

    public static boolean canPlace(LevelAccessor level, BlockPos root, Direction facing, int width) {
        var layout = new AirlockMultiblockLayout(width);
        for (int offset : layout.horizontalOffsets()) {
            BlockPos column = root.relative(facing.getClockWise(), offset);
            for (int y = 0; y < layout.heightBlocks(); y++) {
                BlockPos position = column.above(y);
                if (!position.equals(root) && !level.getBlockState(position).canBeReplaced()) return false;
            }
        }
        return true;
    }
}
