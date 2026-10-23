package net.ssc14.content.block.surface;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Standard ascending staircase shared by the steel, white, and dark resource variants. */
public class StaircaseBlock extends DirectionalStaircaseBlock {
    public StaircaseBlock(BlockBehaviour.Properties properties) {
        super(properties, StaircaseBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(
                    Block.box(0, 2, -1, 16, 4, 4), Block.box(0, 6, 3, 16, 8, 8),
                    Block.box(0, 10, 7, 16, 12, 12), Block.box(0, 14, 11, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 2, 4), Block.box(0, 0, 4, 16, 6, 8),
                    Block.box(0, 0, 8, 16, 10, 12), Block.box(0, 0, 12, 16, 14, 16));
            case EAST -> Shapes.or(
                    Block.box(12, 2, 0, 17, 4, 16), Block.box(8, 6, 0, 13, 8, 16),
                    Block.box(4, 10, 0, 9, 12, 16), Block.box(0, 14, 0, 5, 16, 16),
                    Block.box(12, 0, 0, 16, 2, 16), Block.box(8, 0, 0, 12, 6, 16),
                    Block.box(4, 0, 0, 8, 10, 16), Block.box(0, 0, 0, 4, 14, 16));
            case WEST -> Shapes.or(
                    Block.box(-1, 2, 0, 4, 4, 16), Block.box(3, 6, 0, 8, 8, 16),
                    Block.box(7, 10, 0, 12, 12, 16), Block.box(11, 14, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 2, 16), Block.box(4, 0, 0, 8, 6, 16),
                    Block.box(8, 0, 0, 12, 10, 16), Block.box(12, 0, 0, 16, 14, 16));
            default -> Shapes.or(
                    Block.box(0, 2, 12, 16, 4, 17), Block.box(0, 6, 8, 16, 8, 13),
                    Block.box(0, 10, 4, 16, 12, 9), Block.box(0, 14, 0, 16, 16, 5),
                    Block.box(0, 0, 12, 16, 2, 16), Block.box(0, 0, 8, 16, 6, 12),
                    Block.box(0, 0, 4, 16, 10, 8), Block.box(0, 0, 0, 16, 14, 4));
        };
    }
}
