package net.ssc14.content.energy;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ChunkPos;
import net.mcreator.ssc.energy.EnergyTopologyService;

/** Canonical runtime boundary for energy topology and network snapshots. */
public final class EnergyNetworkService {
    private EnergyNetworkService() { }

    public static void queueUpdate(LevelAccessor level, BlockPos pos) {
        EnergyTopologyService.invalidate(level, pos);
    }

    public static void applyPendingUpdates(LevelAccessor level) {
        EnergyTopologyService.refreshPending(level);
    }

    public static void invalidate(LevelAccessor level, BlockPos pos) {
        EnergyTopologyService.invalidate(level, pos);
    }

    public static List<EnergyNetwork> networks(LevelAccessor level, CableType type) {
        return EnergyTopologyService.networks(level, type);
    }

    public static EnergyNetwork networkAt(LevelAccessor level, CableType type, BlockPos pos) {
        return EnergyTopologyService.networkAt(level, type, pos);
    }

    public static void chunkAvailabilityChanged(LevelAccessor level, ChunkPos chunk) {
        EnergyTopologyService.chunkAvailabilityChanged(level, chunk);
    }

    public static void unloadWorld(LevelAccessor level) {
        EnergyTopologyService.unloadWorld(level);
    }
}
