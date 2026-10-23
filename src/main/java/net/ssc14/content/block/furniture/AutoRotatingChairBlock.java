package net.ssc14.content.block.furniture;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Chair variant that follows the yaw of its seated player. */
public abstract class AutoRotatingChairBlock extends DirectionalChairBlock {
    protected AutoRotatingChairBlock(
            BlockBehaviour.Properties properties, SoundType sound,
            Function<Direction, VoxelShape> shapeProvider) {
        super(properties, sound, shapeProvider);
    }

    @Override
    public void entityInside(
            BlockState state, Level world, BlockPos pos, Entity entity,
            InsideBlockEffectApplier effectApplier, boolean precise) {
        super.entityInside(state, world, pos, entity, effectApplier, precise);
        ChairInteractionService.synchronizeRotation(world, pos);
    }
}
