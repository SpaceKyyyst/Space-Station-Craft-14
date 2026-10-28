package net.ssc14.content.block.furniture;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Wooden chair geometry. */
public class WoodChairBlock extends DirectionalChairBlock {
    public WoodChairBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.WOOD, WoodChairBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(2, 0, 2, 4, 6, 4), Block.box(2, 0, 12, 4, 6, 14),
                    Block.box(12, 0, 12, 14, 6, 14), Block.box(12, 0, 2, 14, 6, 4),
                    Block.box(2, 6, 2, 14, 9, 14), Block.box(2, 11, 12, 14, 21, 14),
                    Block.box(3, 9, 12, 13, 11, 14));
            case EAST -> Shapes.or(Block.box(12, 0, 2, 14, 6, 4), Block.box(2, 0, 2, 4, 6, 4),
                    Block.box(2, 0, 12, 4, 6, 14), Block.box(12, 0, 12, 14, 6, 14),
                    Block.box(2, 6, 2, 14, 9, 14), Block.box(2, 11, 2, 4, 21, 14),
                    Block.box(2, 9, 3, 4, 11, 13));
            case WEST -> Shapes.or(Block.box(2, 0, 12, 4, 6, 14), Block.box(12, 0, 12, 14, 6, 14),
                    Block.box(12, 0, 2, 14, 6, 4), Block.box(2, 0, 2, 4, 6, 4),
                    Block.box(2, 6, 2, 14, 9, 14), Block.box(12, 11, 2, 14, 21, 14),
                    Block.box(12, 9, 3, 14, 11, 13));
            default -> Shapes.or(Block.box(12, 0, 12, 14, 6, 14), Block.box(12, 0, 2, 14, 6, 4),
                    Block.box(2, 0, 2, 4, 6, 4), Block.box(2, 0, 12, 4, 6, 14),
                    Block.box(2, 6, 2, 14, 9, 14), Block.box(2, 11, 2, 14, 21, 4),
                    Block.box(3, 9, 2, 13, 11, 4));
        };
    }
}
