package net.mcreator.ssc;

import java.util.List;
import net.ssc14.content.energy.EnergyNetworkService;
import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/** Stable facade for generated block and procedure integration points. */
public final class EnergyNetworkManager {
    private EnergyNetworkManager() { }
    public static void queueUpdate(LevelAccessor level, BlockPos pos) { EnergyNetworkService.queueUpdate(level, pos); }
    public static void applyPendingUpdates(LevelAccessor level) { EnergyNetworkService.applyPendingUpdates(level); }
    public static void invalidate(LevelAccessor level, BlockPos pos) { EnergyNetworkService.invalidate(level, pos); }
    public static void refreshPending(LevelAccessor level) { EnergyNetworkService.applyPendingUpdates(level); }
    public static List<EnergyNetwork> getNetworks(LevelAccessor level, CableType type) {
        return EnergyNetworkService.networks(level, type);
    }
    /** Invalidates immediately and advances the shared budget; completion may take several ticks. */
    public static void updatePosition(LevelAccessor level, BlockPos pos) {
        invalidate(level, pos);
        refreshPending(level);
    }
}
