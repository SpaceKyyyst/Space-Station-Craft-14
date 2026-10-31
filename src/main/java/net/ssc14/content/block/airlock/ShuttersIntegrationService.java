package net.ssc14.content.block.airlock;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Owns canonical shutters state, interaction and animation integration. */
public final class ShuttersIntegrationService {
    private static Adapter adapter = new CanonicalAdapter();

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

    private static final class CanonicalAdapter implements Adapter {
        @Override
        public void update(Level level, BlockPos pos, BlockState state) {
            ShuttersStateService.update(level, pos, state);
        }

        @Override
        public void use(Level level, BlockPos pos, BlockState state, Player player) {
            ShuttersInteractionService.use(level, pos, state, player);
        }

        @Override
        public void setOpen(Level level, BlockPos pos, BlockState state, boolean open) {
            ShuttersAnimationService.setOpen(level, pos, open);
        }
    }
}
