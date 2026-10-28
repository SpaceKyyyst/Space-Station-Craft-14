package net.ssc14.content.block.gravity;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Migration boundary for gravity-generator proxy lifecycle procedures. */
public final class GravityGeneratorPlugLifecycleService {
    public interface Adapter {
        void neighborChanged(Level level, BlockPos pos);

        void destroyed(Level level, BlockPos pos);
    }

    public interface GeneratorLocator {
        boolean isGenerator(Level level, BlockPos pos);
    }

    public interface PlugBlockFactory {
        BlockState defaultState();
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public void neighborChanged(Level level, BlockPos pos) { }

        @Override
        public void destroyed(Level level, BlockPos pos) { }
    };
    private static GeneratorLocator generatorLocator = (level, pos) -> false;
    private static PlugBlockFactory plugBlockFactory = () -> Blocks.AIR.defaultBlockState();

    private GravityGeneratorPlugLifecycleService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void installGeneratorLocator(GeneratorLocator replacement) {
        generatorLocator = Objects.requireNonNull(replacement, "replacement");
    }

    public static void installPlugBlockFactory(PlugBlockFactory replacement) {
        plugBlockFactory = Objects.requireNonNull(replacement, "replacement");
    }

    public static void generate(Level level, BlockPos pos, BlockState generatorState) {
        net.minecraft.core.Direction facing = generatorState.hasProperty(
                net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING)
                ? generatorState.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING)
                : net.minecraft.core.Direction.NORTH;
        BlockPos[] offsets = switch (facing) {
            case NORTH -> new BlockPos[]{new BlockPos(0, 0, 1), new BlockPos(0, 1, 1), new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 1, 0)};
            case SOUTH -> new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(0, 1, -1), new BlockPos(1, 0, 0), new BlockPos(1, 1, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 1, 0)};
            case WEST -> new BlockPos[]{new BlockPos(0, 0, 1), new BlockPos(0, 1, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, -1), new BlockPos(1, 0, 0), new BlockPos(1, 1, 0)};
            default -> new BlockPos[]{new BlockPos(0, 0, 1), new BlockPos(0, 1, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, -1), new BlockPos(-1, 0, 0), new BlockPos(-1, 1, 0)};
        };
        for (BlockPos offset : offsets) {
            level.setBlock(pos.offset(offset), plugBlockFactory.defaultState(), 3);
        }
    }

    public static void validate(Level level, BlockPos pos) {
        if (level.isClientSide() || !generatorLocator.isGenerator(level, pos)) {
            if (!level.isClientSide()) level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    public static void destroyGenerator(Level level, BlockPos pos) {
        BlockPos[] offsets = {new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, -1, 1), new BlockPos(0, -1, -1), new BlockPos(1, -1, 0), new BlockPos(-1, -1, 0)};
        for (BlockPos offset : offsets) {
            BlockPos candidate = pos.offset(offset);
            if (generatorLocator.isGenerator(level, candidate)) {
                level.destroyBlock(candidate, false);
                return;
            }
        }
    }

    public static void neighborChanged(Level level, BlockPos pos) {
        adapter.neighborChanged(level, pos);
    }

    public static void destroyed(Level level, BlockPos pos) {
        adapter.destroyed(level, pos);
    }
}
