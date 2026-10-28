package net.ssc14.content.block.lighting;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Station-code indicator that refreshes its eight-state signal once per second. */
public class EmergencyLightBlock extends WallMountedLightBlock implements EntityBlock {
    public static final IntegerProperty ST_CODE = IntegerProperty.create("st_code", 0, 7);
    private static final int UPDATE_INTERVAL_TICKS = 20;

    private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory;

    public EmergencyLightBlock(BlockBehaviour.Properties properties,
            BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory) {
        super(emissive(properties), Geometry.EMERGENCY, 5.0F, 0);
        this.blockEntityFactory = blockEntityFactory;
        registerDefaultState(defaultBlockState().setValue(ST_CODE, 0));
    }

    private static BlockBehaviour.Properties emissive(BlockBehaviour.Properties properties) {
        return properties
                .postProcess((state, level, pos) -> pos)
                .emissiveRendering((state, level, pos) -> true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ST_CODE);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 0;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.apply(pos, state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        level.scheduleTick(pos, this, UPDATE_INTERVAL_TICKS);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        EmergencyLightingService.synchronize(level, pos, state);
        level.scheduleTick(pos, this, UPDATE_INTERVAL_TICKS);
    }
}
