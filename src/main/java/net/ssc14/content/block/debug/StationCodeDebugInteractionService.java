package net.ssc14.content.block.debug;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Migration boundary for the debug block's station-code interaction. */
public final class StationCodeDebugInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void interact(Level level, BlockPos pos, Player player);
    }

    private static Adapter adapter = (level, pos, player) -> { };

    private StationCodeDebugInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void interact(Level level, BlockPos pos, Player player) {
        adapter.interact(level, pos, player);
    }
}
