package net.ssc14.content.block.atmosphere;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ssc14.content.atmosphere.AtmosphericBlock;

/** Canonical always-permeable oxygen producer with constant-time scheduled work. */
public class GasProducerO2Block extends Block implements AtmosphericBlock {
    private static final int TICK_INTERVAL = 1;
    private static final VoxelShape SHAPE = Shapes.or(
            box(1, 0, 1, 15, 2, 15),
            box(4, 2, 4, 12, 4, 12),
            box(2, -8, 2, 14, 0, 14));

    public GasProducerO2Block(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.ANVIL).strength(-1.0F, 3_600_000.0F).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
    }

    @Override public float getPermeability(BlockState state) { return 1.0F; }

    @Override
    public VoxelShape getShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override public boolean propagatesSkylightDown(BlockState state) { return true; }
    @Override public int getLightDampening(BlockState state) { return 0; }

    @Override
    public VoxelShape getVisualShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState,
            boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        GasProducerO2TickService.tick(level, pos);
        level.scheduleTick(pos, this, TICK_INTERVAL);
    }
}
