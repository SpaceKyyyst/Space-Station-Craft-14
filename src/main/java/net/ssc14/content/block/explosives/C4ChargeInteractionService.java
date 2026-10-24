package net.ssc14.content.block.explosives;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Migration boundary between canonical C4 content and legacy procedures. */
public final class C4ChargeInteractionService {
    public interface Adapter {
        void plant(Level level, BlockPos pos);

        void activate(Level level, BlockPos pos, Player player);

        void detonate(Level level, BlockPos pos);
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public void plant(Level level, BlockPos pos) { }

        @Override
        public void activate(Level level, BlockPos pos, Player player) { }

        @Override
        public void detonate(Level level, BlockPos pos) { }
    };

    private C4ChargeInteractionService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void plant(Level level, BlockPos pos) {
        adapter.plant(level, pos);
    }

    public static void activate(Level level, BlockPos pos, Player player) {
        adapter.activate(level, pos, player);
    }

    public static void detonate(Level level, BlockPos pos) {
        adapter.detonate(level, pos);
    }
}
