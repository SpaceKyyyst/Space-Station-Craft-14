package net.ssc14.content.block.gravity;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Migration boundary for generated gravity-generator lifecycle procedures. */
public final class GravityGeneratorLifecycleService {
    public interface Adapter {
        boolean canSurvive(LevelAccessor level, BlockPos pos);

        void placed(Level level, BlockPos pos, BlockState state);

        void tick(ServerLevel level, BlockPos pos, BlockState state);

        void destroyed();
    }

    private static Adapter adapter = new Adapter() {
        @Override public boolean canSurvive(LevelAccessor level, BlockPos pos) { return true; }
        @Override public void placed(Level level, BlockPos pos, BlockState state) { }
        @Override public void tick(ServerLevel level, BlockPos pos, BlockState state) { }
        @Override public void destroyed() { }
    };

    private GravityGeneratorLifecycleService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static boolean canSurvive(LevelAccessor level, BlockPos pos) {
        return adapter.canSurvive(level, pos);
    }

    public static void placed(Level level, BlockPos pos, BlockState state) {
        adapter.placed(level, pos, state);
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state) {
        adapter.tick(level, pos, state);
    }

    public static void destroyed() {
        adapter.destroyed();
    }
}
