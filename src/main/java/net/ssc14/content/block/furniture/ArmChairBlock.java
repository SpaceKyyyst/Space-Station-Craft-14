package net.ssc14.content.block.furniture;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared implementation for the three armchair visual variants. */
public class ArmChairBlock extends DirectionalChairBlock {
    private static final float STRENGTH = 7.5f;

    public ArmChairBlock(BlockBehaviour.Properties properties) {
        super(properties, SoundType.IRON, STRENGTH, ArmChairBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(1, 7, 2, 4, 11, 13), Block.box(12, 7, 2, 15, 11, 13),
                    Block.box(2, 7, 11, 14, 19, 15));
            case EAST -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(3, 7, 1, 14, 11, 4), Block.box(3, 7, 12, 14, 11, 15),
                    Block.box(1, 7, 2, 5, 19, 14));
            case WEST -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(2, 7, 12, 13, 11, 15), Block.box(2, 7, 1, 13, 11, 4),
                    Block.box(11, 7, 2, 15, 19, 14));
            default -> Shapes.or(Block.box(2, 1, 2, 14, 8, 14),
                    Block.box(12, 7, 3, 15, 11, 14), Block.box(1, 7, 3, 4, 11, 14),
                    Block.box(2, 7, 1, 14, 19, 5));
        };
    }
}
