package net.ssc14.content.block.floor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Adds the historical airlock seal to a floor tile's collision shape. */
public final class FloorTileDoorwayCollision {
    private FloorTileDoorwayCollision() {
    }

    public static VoxelShape collision(BlockGetter level, BlockPos floor, VoxelShape tile) {
        var door = level.getBlockState(floor.above());
        if (!(door.getBlock().getStateDefinition().getProperty("arl_variat")
                instanceof IntegerProperty property)) {
            return tile;
        }

        int frame = door.getValue(property);
        boolean open = frame == 11 || frame == 13 || frame == 14;
        boolean alongX = door.getValue(HorizontalDirectionalBlock.FACING).getAxis()
                == Direction.Axis.Z;
        VoxelShape seal = alongX
                ? (open
                        ? Shapes.or(Block.box(0, 0, 5, 1, 16, 11),
                                Block.box(15, 0, 5, 16, 16, 11))
                        : Block.box(0, 0, 5, 16, 16, 11))
                : (open
                        ? Shapes.or(Block.box(5, 0, 0, 11, 16, 1),
                                Block.box(5, 0, 15, 11, 16, 16))
                        : Block.box(5, 0, 0, 11, 16, 16));
        return Shapes.or(tile, seal);
    }
}
