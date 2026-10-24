package net.ssc14.content.block.communication;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Migration boundary for generated communication-server procedures. */
public final class CommunicationServerLifecycleService {
    public interface Adapter {
        void tick(ServerLevel level, BlockPos pos, BlockState state);
        void use(Level level, BlockPos pos, BlockState state, Player player);
    }

    private static Adapter adapter = new Adapter() {
        @Override public void tick(ServerLevel level, BlockPos pos, BlockState state) { }
        @Override public void use(Level level, BlockPos pos, BlockState state, Player player) { }
    };

    private CommunicationServerLifecycleService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state) {
        adapter.tick(level, pos, state);
    }

    public static void use(Level level, BlockPos pos, BlockState state, Player player) {
        adapter.use(level, pos, state, player);
    }
}
