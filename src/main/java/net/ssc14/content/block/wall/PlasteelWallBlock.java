package net.ssc14.content.block.wall;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PlasteelWallBlock extends AbstractStationWallBlock {
    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 8);

    public PlasteelWallBlock(BlockBehaviour.Properties properties) {
        super(properties, 300.0f, 16.5f);
        registerDefaultState(stateDefinition.any().setValue(BLOCKSTATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLOCKSTATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(BLOCKSTATE, 0);
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        WallDamageService.schedulePlasteelExplosion(level, pos.getX(), pos.getY(), pos.getZ());
    }
}
