package net.ssc14.content.block.decoration;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceLineBlock extends DirectionalFenceBlock {
    public FenceLineBlock(BlockBehaviour.Properties properties) {
        super(properties, FenceLineBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Block.box(0, 0, 14, 16, 14, 16);
            case EAST -> Block.box(0, 0, 0, 2, 14, 16);
            case WEST -> Block.box(14, 0, 0, 16, 14, 16);
            default -> Block.box(0, 0, 0, 16, 14, 2);
        };
    }
}
