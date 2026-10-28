package net.ssc14.content.block.communication;

import java.util.Objects;
import net.minecraft.world.level.LevelAccessor;

/** Persistence port for communication-server radio-frequency flags. */
public final class CommunicationFrequencyStateService {
    @FunctionalInterface
    public interface Writer { void set(LevelAccessor level, String key, boolean enabled); }

    private static volatile Writer writer = (level, key, enabled) -> { };

    private CommunicationFrequencyStateService() { }

    public static void install(Writer replacement) {
        writer = Objects.requireNonNull(replacement, "replacement");
    }

    public static void set(LevelAccessor level, String stateKey, boolean enabled) {
        if (level != null && !level.isClientSide() && stateKey != null) {
            writer.set(level, stateKey, enabled);
        }
    }
}
