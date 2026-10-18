package net.mcreator.ssc.block;

import net.mcreator.ssc.access.AirlockFloor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Invisible lower part; the root renderer draws it. Replaced by a tile without losing its seal. */
public final class AirlockThresholdBlock extends Block {
    public AirlockThresholdBlock(Properties properties) {
        super(properties.strength(-1).noOcclusion().dynamicShape());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.INVISIBLE; }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AirlockFloor.collision(level, pos, Shapes.empty());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor, Orientation orientation, boolean moving) {
        if (!level.isClientSide() && !AirlockFloor.hasDoor(level, pos)) level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
    }
}
