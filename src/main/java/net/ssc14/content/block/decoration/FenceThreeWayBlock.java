package net.ssc14.content.block.decoration;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceThreeWayBlock extends DirectionalFenceBlock {
    public FenceThreeWayBlock(BlockBehaviour.Properties properties) {
        super(properties, FenceThreeWayBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Shapes.or(Block.box(0, 0, 14, 16, 14, 16), Block.box(0, 0, 0, 2, 14, 16), Block.box(14, 0, 0, 16, 14, 16));
            case EAST -> Shapes.or(Block.box(0, 0, 0, 2, 14, 16), Block.box(0, 0, 0, 16, 14, 2), Block.box(0, 0, 14, 16, 14, 16));
            case WEST -> Shapes.or(Block.box(14, 0, 0, 16, 14, 16), Block.box(0, 0, 14, 16, 14, 16), Block.box(0, 0, 0, 16, 14, 2));
            default -> Shapes.or(Block.box(0, 0, 0, 16, 14, 2), Block.box(14, 0, 0, 16, 14, 16), Block.box(0, 0, 0, 2, 14, 16));
        };
    }
}
