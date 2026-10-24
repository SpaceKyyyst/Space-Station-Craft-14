package net.ssc14.content.block.floor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Floor tile whose visual state is chosen once when placed on the server. */
public abstract class RandomizedFloorTileBlock extends FloorTileBlock {
    public static final int MINIMUM_STATE = 1;

    protected RandomizedFloorTileBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(stateProperty(), MINIMUM_STATE));
    }

    public abstract IntegerProperty stateProperty();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(stateProperty());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        if (level.isClientSide() || oldState.is(this)) {
            return;
        }
        int maximumState = stateProperty().getPossibleValues().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
        int selectedState = MINIMUM_STATE + level.getRandom().nextInt(maximumState);
        level.setBlock(pos, state.setValue(stateProperty(), selectedState), Block.UPDATE_ALL);
    }
}
