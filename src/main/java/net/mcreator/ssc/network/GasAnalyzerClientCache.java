package net.mcreator.ssc.network;

import java.lang.ref.WeakReference;
import java.util.Optional;
import net.minecraft.world.level.Level;

/** Client-thread cache tied to one world instance; it cannot retain a disconnected world. */
public final class GasAnalyzerClientCache {
    private static final long MAX_AGE_TICKS = 40;
    private static WeakReference<Level> sampledLevel = new WeakReference<>(null);
    private static GasAnalyzerSnapshotMessage snapshot;
    private static long receivedAt;

    private GasAnalyzerClientCache() {
    }

    public static void accept(Level level, GasAnalyzerSnapshotMessage message) {
        if (!level.isClientSide() || !level.dimension().identifier().equals(message.dimension())) {
            return;
        }
        sampledLevel = new WeakReference<>(level);
        snapshot = message;
        receivedAt = level.getGameTime();
    }

    public static Optional<GasAnalyzerSnapshotMessage> get(Level level) {
        if (level == null || level != sampledLevel.get() || snapshot == null
                || !level.dimension().identifier().equals(snapshot.dimension())) {
            return Optional.empty();
        }
        long age = level.getGameTime() - receivedAt;
        return age >= 0 && age <= MAX_AGE_TICKS ? Optional.of(snapshot) : Optional.empty();
    }
}
