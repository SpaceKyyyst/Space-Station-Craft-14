package net.ssc14.content.block.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

/** Repairable wall light state shared by tube and technical fixtures. */
public class ServiceableLightBlock extends WallMountedLightBlock {
    public static final BooleanProperty HAVE_LAMP = BooleanProperty.create("have_lamp");
    public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public ServiceableLightBlock(BlockBehaviour.Properties properties, Geometry geometry) {
        super(properties, geometry, 5.0F, 0);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAVE_LAMP, true)
                .setValue(BROKEN, false)
                .setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HAVE_LAMP, BROKEN, ACTIVE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state
                .setValue(HAVE_LAMP, true)
                .setValue(BROKEN, false)
                .setValue(ACTIVE, false);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        interact(level, pos, state, player);
        return InteractionResult.SUCCESS;
    }

    protected void interact(Level level, BlockPos pos, BlockState state, Player player) {
        // The generated legacy procedure currently targets a removed integer property.
        // Keeping the canonical hook empty preserves the effective runtime behavior.
    }
}
