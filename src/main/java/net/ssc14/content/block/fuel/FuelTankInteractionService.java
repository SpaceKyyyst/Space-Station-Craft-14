package net.ssc14.content.block.fuel;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Boundary between the canonical fuel block and transitional gameplay procedures. */
public final class FuelTankInteractionService {
    public interface Adapter {
        void initialize(Level level, BlockPos pos);
        void interact(Level level, BlockPos pos, Player player);
    }

    private static Adapter adapter = new Adapter() {
        @Override public void initialize(Level level, BlockPos pos) { }
        @Override public void interact(Level level, BlockPos pos, Player player) { }
    };

    private FuelTankInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void initialize(Level level, BlockPos pos) {
        adapter.initialize(level, pos);
    }

    public static void interact(Level level, BlockPos pos, Player player) {
        adapter.interact(level, pos, player);
    }
}
