package net.ssc14.content.block.storage;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Migration boundary for the upper closet plug's legacy access interaction. */
public final class UpperClosetPlugInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void interact(Level level, BlockPos pos, Player player);
    }

    private static Adapter adapter = (level, pos, player) -> { };

    private UpperClosetPlugInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void interact(Level level, BlockPos pos, Player player) {
        if (level != null && level.isClientSide()) return;
        adapter.interact(level, pos, player);
    }
}
