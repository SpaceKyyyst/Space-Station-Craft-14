package net.ssc14.content.block.airlock;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Integration boundary between canonical shutters and legacy procedures. */
public final class ShuttersIntegrationService {
    private static Adapter adapter = new Adapter() {
        @Override public void update(Level level, BlockPos pos, BlockState state) { }
        @Override public void use(Level level, BlockPos pos, BlockState state, Player player) { }
        @Override public void setOpen(Level level, BlockPos pos, BlockState state, boolean open) { }
    };

    private ShuttersIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void update(Level level, BlockPos pos, BlockState state) {
        adapter.update(level, pos, state);
    }

    public static void use(Level level, BlockPos pos, BlockState state, Player player) {
        adapter.use(level, pos, state, player);
    }

    public static void setOpen(Level level, BlockPos pos, BlockState state, boolean open) {
        adapter.setOpen(level, pos, state, open);
    }

    public interface Adapter {
        void update(Level level, BlockPos pos, BlockState state);
        void use(Level level, BlockPos pos, BlockState state, Player player);
        void setOpen(Level level, BlockPos pos, BlockState state, boolean open);
    }
}
