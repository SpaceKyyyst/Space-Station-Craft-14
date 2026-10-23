package net.ssc14.content.block.decoration;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FencePointBlock extends DirectionalFenceBlock {
    public FencePointBlock(BlockBehaviour.Properties properties) {
        super(properties, FencePointBlock::shape);
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Block.box(0, 0, 14, 2, 14, 16);
            case EAST -> Block.box(0, 0, 0, 2, 14, 2);
            case WEST -> Block.box(14, 0, 14, 16, 14, 16);
            default -> Block.box(14, 0, 0, 16, 14, 2);
        };
    }
}
