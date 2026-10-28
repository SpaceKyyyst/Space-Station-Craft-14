package net.ssc14.content.block.surface;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Upper/inverted staircase shared by the steel, white, and dark resource variants. */
public class UpperStaircaseBlock extends DirectionalStaircaseBlock {
    public UpperStaircaseBlock(BlockBehaviour.Properties properties) {
        super(properties, UpperStaircaseBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(
                    Block.box(0, 12, 0, 16, 16, 4), Block.box(0, 8, 4, 16, 16, 8),
                    Block.box(0, 4, 8, 16, 16, 12), Block.box(0, 0, 12, 16, 16, 16));
            case EAST -> Shapes.or(
                    Block.box(12, 12, 0, 16, 16, 16), Block.box(8, 8, 0, 12, 16, 16),
                    Block.box(4, 4, 0, 8, 16, 16), Block.box(0, 0, 0, 4, 16, 16));
            case WEST -> Shapes.or(
                    Block.box(0, 12, 0, 4, 16, 16), Block.box(4, 8, 0, 8, 16, 16),
                    Block.box(8, 4, 0, 12, 16, 16), Block.box(12, 0, 0, 16, 16, 16));
            default -> Shapes.or(
                    Block.box(0, 12, 12, 16, 16, 16), Block.box(0, 8, 8, 16, 16, 12),
                    Block.box(0, 4, 4, 16, 16, 8), Block.box(0, 0, 0, 16, 16, 4));
        };
    }
}
