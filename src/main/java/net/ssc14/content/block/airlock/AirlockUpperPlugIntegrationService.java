package net.ssc14.content.block.airlock;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Integration boundary between canonical upper airlock plugs and legacy procedures. */
public final class AirlockUpperPlugIntegrationService {
    private static Adapter adapter = new Adapter() {
        @Override public void update(Level level, BlockPos pos) { }
        @Override public void destroy(Level level, BlockPos pos) { }
        @Override public void use(Level level, BlockPos pos, Player player) { }
    };

    private AirlockUpperPlugIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void update(Level level, BlockPos pos) { adapter.update(level, pos); }

    public static void destroy(Level level, BlockPos pos) { adapter.destroy(level, pos); }

    public static void use(Level level, BlockPos pos, Player player) {
        adapter.use(level, pos, player);
    }

    public interface Adapter {
        void update(Level level, BlockPos pos);
        void destroy(Level level, BlockPos pos);
        void use(Level level, BlockPos pos, Player player);
    }
}
