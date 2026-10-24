package net.ssc14.content.block.debug;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Debug control that cycles or resets the station code through a migration boundary. */
public class StationCodeDebugBlock extends Block {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 2);
    private final Function<BlockState, VoxelShape> shapes = makeShapes();

    public StationCodeDebugBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.GRAVEL).strength(1.0F, 10.0F));
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0));
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return getShapeForEachState(state -> state.getValue(BLOCKSTATE) == 1
                ? box(2, 0, 2, 14, 32, 14)
                : box(0, 0, 0, 16, 16, 16));
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return shapes.apply(state); }
    @Override protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCKSTATE);
    }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(BLOCKSTATE, 0);
    }
    @Override public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
            Player player, BlockHitResult hit) {
        super.useWithoutItem(state, level, pos, player, hit);
        StationCodeDebugInteractionService.interact(level, pos, player);
        return InteractionResult.SUCCESS;
    }
}
