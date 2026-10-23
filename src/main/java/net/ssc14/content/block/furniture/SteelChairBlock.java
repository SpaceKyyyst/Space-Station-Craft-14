package net.ssc14.content.block.furniture;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Steel chair geometry. */
public class SteelChairBlock extends DirectionalChairBlock {
    public SteelChairBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.IRON, SteelChairBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(2, 0, 2, 4, 7, 4), Block.box(3, 0, 12, 5, 7, 14),
                    Block.box(2, 7, 2, 14, 9, 13), Block.box(3, 7, 13, 5, 19, 15),
                    Block.box(2, 14, 12, 14, 20, 14), Block.box(11, 7, 13, 13, 19, 15),
                    Block.box(12, 0, 2, 14, 7, 4), Block.box(11, 0, 12, 13, 7, 14));
            case EAST -> Shapes.or(Block.box(12, 0, 2, 14, 7, 4), Block.box(2, 0, 3, 4, 7, 5),
                    Block.box(3, 7, 2, 14, 9, 14), Block.box(1, 7, 3, 3, 19, 5),
                    Block.box(2, 14, 2, 4, 20, 14), Block.box(1, 7, 11, 3, 19, 13),
                    Block.box(12, 0, 12, 14, 7, 14), Block.box(2, 0, 11, 4, 7, 13));
            case WEST -> Shapes.or(Block.box(2, 0, 12, 4, 7, 14), Block.box(12, 0, 11, 14, 7, 13),
                    Block.box(2, 7, 2, 13, 9, 14), Block.box(13, 7, 11, 15, 19, 13),
                    Block.box(12, 14, 2, 14, 20, 14), Block.box(13, 7, 3, 15, 19, 5),
                    Block.box(2, 0, 2, 4, 7, 4), Block.box(12, 0, 3, 14, 7, 5));
            default -> Shapes.or(Block.box(12, 0, 12, 14, 7, 14), Block.box(11, 0, 2, 13, 7, 4),
                    Block.box(2, 7, 3, 14, 9, 14), Block.box(11, 7, 1, 13, 19, 3),
                    Block.box(2, 14, 2, 14, 20, 4), Block.box(3, 7, 1, 5, 19, 3),
                    Block.box(2, 0, 12, 4, 7, 14), Block.box(3, 0, 2, 5, 7, 4));
        };
    }
}
