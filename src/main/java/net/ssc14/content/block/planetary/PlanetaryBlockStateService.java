package net.ssc14.content.block.planetary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/** Stateless compatibility behavior for randomized planetary block orientation. */
public final class PlanetaryBlockStateService {
    private PlanetaryBlockStateService() {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void randomizeOrientation(LevelAccessor level, BlockPos pos) {
        Direction direction = Direction.getRandom(RandomSource.create());
        BlockState state = level.getBlockState(pos);
        if (state.getBlock().getStateDefinition().getProperty("facing")
                instanceof EnumProperty facing
                && facing.getPossibleValues().contains(direction)) {
            level.setBlock(pos, state.setValue(facing, direction), 3);
        } else if (state.getBlock().getStateDefinition().getProperty("axis")
                instanceof EnumProperty axis
                && axis.getPossibleValues().contains(direction.getAxis())) {
            level.setBlock(pos, state.setValue(axis, direction.getAxis()), 3);
        }
    }
}
