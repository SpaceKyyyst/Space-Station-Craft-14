package net.ssc14.content.block.storage;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Migration boundary for secure-closet access and lock interactions. */
public final class SecureClosetInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void interact(Level level, BlockPos pos, BlockState state, Player player);
    }

    private static Adapter adapter = (level, pos, state, player) -> { };

    private SecureClosetInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void interact(Level level, BlockPos pos, BlockState state, Player player) {
        adapter.interact(level, pos, state, player);
    }
}
