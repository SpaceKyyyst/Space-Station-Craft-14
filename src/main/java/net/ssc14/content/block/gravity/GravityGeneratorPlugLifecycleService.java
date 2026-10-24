package net.ssc14.content.block.gravity;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** Migration boundary for gravity-generator proxy lifecycle procedures. */
public final class GravityGeneratorPlugLifecycleService {
    public interface Adapter {
        void neighborChanged(Level level, BlockPos pos);

        void destroyed(Level level, BlockPos pos);
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public void neighborChanged(Level level, BlockPos pos) { }

        @Override
        public void destroyed(Level level, BlockPos pos) { }
    };

    private GravityGeneratorPlugLifecycleService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void neighborChanged(Level level, BlockPos pos) {
        adapter.neighborChanged(level, pos);
    }

    public static void destroyed(Level level, BlockPos pos) {
        adapter.destroyed(level, pos);
    }
}
