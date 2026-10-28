package net.ssc14.content.block.lighting;

import java.util.Objects;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/** Bridges light block-entity lifecycle events to the installed energy implementation. */
public final class LightingEnergyService {
    private static volatile BiConsumer<LevelAccessor, BlockPos> updateConsumer =
            (level, pos) -> { };

    private LightingEnergyService() { }

    public static void install(BiConsumer<LevelAccessor, BlockPos> consumer) {
        updateConsumer = Objects.requireNonNull(consumer, "consumer");
    }

    public static void queueUpdate(LevelAccessor level, BlockPos pos) {
        updateConsumer.accept(level, pos);
    }
}
