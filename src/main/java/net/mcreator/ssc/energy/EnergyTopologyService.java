package net.mcreator.ssc.energy;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;

/** @deprecated Use {@link net.ssc14.content.energy.EnergyTopologyService}. */
@Deprecated
public final class EnergyTopologyService {
    public static final int MAX_WORK_UNITS_PER_TICK =
            net.ssc14.content.energy.EnergyTopologyService.MAX_WORK_UNITS_PER_TICK;

    private EnergyTopologyService() { }

    public static void invalidate(LevelAccessor level, BlockPos pos) {
        net.ssc14.content.energy.EnergyTopologyService.invalidate(level, pos);
    }

    public static void refreshPending(LevelAccessor level) {
        net.ssc14.content.energy.EnergyTopologyService.refreshPending(level);
    }

    public static List<EnergyNetwork> networks(LevelAccessor level, CableType type) {
        return net.ssc14.content.energy.EnergyTopologyService.networks(level, type);
    }

    public static EnergyNetwork networkAt(LevelAccessor level, CableType type, BlockPos pos) {
        return net.ssc14.content.energy.EnergyTopologyService.networkAt(level, type, pos);
    }

    public static void chunkAvailabilityChanged(LevelAccessor level, ChunkPos chunk) {
        net.ssc14.content.energy.EnergyTopologyService.chunkAvailabilityChanged(level, chunk);
    }

    public static void unloadWorld(LevelAccessor level) {
        net.ssc14.content.energy.EnergyTopologyService.unloadWorld(level);
    }

    public static int workUnitsThisTick(LevelAccessor level) {
        return net.ssc14.content.energy.EnergyTopologyService.workUnitsThisTick(level);
    }

    public static boolean rebuilding(LevelAccessor level) {
        return net.ssc14.content.energy.EnergyTopologyService.rebuilding(level);
    }
}
