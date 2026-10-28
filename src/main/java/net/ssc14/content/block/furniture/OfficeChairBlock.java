package net.ssc14.content.block.furniture;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared black/white office-chair geometry and autorotation behavior. */
public class OfficeChairBlock extends AutoRotatingChairBlock {
    public OfficeChairBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.CALCITE, OfficeChairBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(4, 13, 12, 12, 22, 14), Block.box(5, 14, 14, 11, 21, 15),
                    Block.box(7, 9, 13, 9, 14, 15), Block.box(7, 8, 12, 9, 9, 14),
                    Block.box(3, 9, 3, 13, 11, 13), Block.box(4, 8, 4, 12, 9, 12));
            case EAST -> Shapes.or(Block.box(2, 13, 4, 4, 22, 12), Block.box(1, 14, 5, 2, 21, 11),
                    Block.box(1, 9, 7, 3, 14, 9), Block.box(2, 8, 7, 4, 9, 9),
                    Block.box(3, 9, 3, 13, 11, 13), Block.box(4, 8, 4, 12, 9, 12));
            case WEST -> Shapes.or(Block.box(12, 13, 4, 14, 22, 12), Block.box(14, 14, 5, 15, 21, 11),
                    Block.box(13, 9, 7, 15, 14, 9), Block.box(12, 8, 7, 14, 9, 9),
                    Block.box(3, 9, 3, 13, 11, 13), Block.box(4, 8, 4, 12, 9, 12));
            default -> Shapes.or(Block.box(4, 13, 2, 12, 22, 4), Block.box(5, 14, 1, 11, 21, 2),
                    Block.box(7, 9, 1, 9, 14, 3), Block.box(7, 8, 2, 9, 9, 4),
                    Block.box(3, 9, 3, 13, 11, 13), Block.box(4, 8, 4, 12, 9, 12));
        };
    }
}
