package net.ssc14.content.block.energy;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/** Boundary between cable content lifecycle and the legacy energy topology engine. */
public final class CableTopologyService {
    private static final Adapter NO_OP = new Adapter() {
        @Override public void queueUpdate(LevelAccessor level, BlockPos pos) { }
        @Override public void invalidate(LevelAccessor level, BlockPos pos) { }
    };
    private static volatile Adapter adapter = NO_OP;

    private CableTopologyService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void queueUpdate(LevelAccessor level, BlockPos pos) {
        if (level != null) {
            adapter.queueUpdate(level, pos);
        }
    }

    public static void invalidate(LevelAccessor level, BlockPos pos) {
        adapter.invalidate(level, pos);
    }

    public interface Adapter {
        void queueUpdate(LevelAccessor level, BlockPos pos);
        void invalidate(LevelAccessor level, BlockPos pos);
    }
}
