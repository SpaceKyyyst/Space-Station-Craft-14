package net.ssc14.content.block.furniture;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Bar-chair geometry and autorotation behavior. */
public class BarChairBlock extends AutoRotatingChairBlock {
    public BarChairBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.IRON, BarChairBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(3, 8, 3, 13, 11, 13), Block.box(5, 7, 5, 11, 8, 11),
                    Block.box(5, 8, 13, 11, 14, 14), Block.box(4, 0, 4, 12, 1, 12),
                    Block.box(7, 1, 7, 9, 7, 9));
            case EAST -> Shapes.or(Block.box(3, 8, 3, 13, 11, 13), Block.box(5, 7, 5, 11, 8, 11),
                    Block.box(2, 8, 5, 3, 14, 11), Block.box(4, 0, 4, 12, 1, 12),
                    Block.box(7, 1, 7, 9, 7, 9));
            case WEST -> Shapes.or(Block.box(3, 8, 3, 13, 11, 13), Block.box(5, 7, 5, 11, 8, 11),
                    Block.box(13, 8, 5, 14, 14, 11), Block.box(4, 0, 4, 12, 1, 12),
                    Block.box(7, 1, 7, 9, 7, 9));
            default -> Shapes.or(Block.box(3, 8, 3, 13, 11, 13), Block.box(5, 7, 5, 11, 8, 11),
                    Block.box(5, 8, 2, 11, 14, 3), Block.box(4, 0, 4, 12, 1, 12),
                    Block.box(7, 1, 7, 9, 7, 9));
        };
    }
}
