package net.ssc14.content.block.communication;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Migration boundary for generated communication-server procedures. */
public final class CommunicationServerLifecycleService {
    private static final int RESET_DELAY_TICKS = 20;

    public interface FrequencyState {
        void setEnabled(String stateKey, boolean enabled);
    }
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

    /** Enables one radio channel and clears it when the server block no longer matches. */
    public static void activate(ServerLevel level, BlockPos pos, BlockState state,
            FrequencyState frequencyState) {
        if (state == null || frequencyState == null) return;
        var property = state.getBlock().getStateDefinition().getProperty("type");
        if (!(property instanceof net.minecraft.world.level.block.state.properties.IntegerProperty type)) return;
        int blockType = state.getValue(type);
        CommunicationFrequency frequency = CommunicationFrequency.fromBlockType(blockType);
        if (frequency == null) return;
        frequencyState.setEnabled(frequency.stateKey(), true);
        ServerWorkScheduler.schedule(level, RESET_DELAY_TICKS, () -> {
            BlockState current = level.getBlockState(pos);
            if (!matchesType(current, state.getBlock(), blockType)) {
                frequencyState.setEnabled(frequency.stateKey(), false);
            }
        });
    }

    static boolean matchesType(BlockState current, net.minecraft.world.level.block.Block expectedBlock, int expectedType) {
        if (current == null || current.getBlock() != expectedBlock) return false;
        var property = current.getBlock().getStateDefinition().getProperty("type");
        return property instanceof net.minecraft.world.level.block.state.properties.IntegerProperty type
                && current.getValue(type) == expectedType;
    }
}
