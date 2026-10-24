package net.ssc14.content.block.floor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;

/** Floor tile variant whose corner posts stop below the top plate. */
public class InsetFloorTileBlock extends AbstractFloorTileBlock {
    private static final net.minecraft.world.phys.shapes.VoxelShape SHAPE = Shapes.or(
            Block.box(0, 12, 0, 16, 16, 16),
            Block.box(0, 0, 0, 1, 14, 1),
            Block.box(0, 0, 15, 1, 14, 16),
            Block.box(15, 0, 0, 16, 14, 1),
            Block.box(15, 0, 15, 16, 14, 16));

    public InsetFloorTileBlock(BlockBehaviour.Properties properties) {
        super(properties, SHAPE);
    }
}
