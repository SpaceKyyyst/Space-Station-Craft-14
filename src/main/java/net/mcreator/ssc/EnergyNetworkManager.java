package net.mcreator.ssc;

import java.util.List;
import net.mcreator.ssc.energy.EnergyTopologyService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/** Stable facade for generated block and procedure integration points. */
public final class EnergyNetworkManager {
    private EnergyNetworkManager() { }
    public static void invalidate(LevelAccessor level, BlockPos pos) { EnergyTopologyService.invalidate(level, pos); }
    public static void refreshPending(LevelAccessor level) { EnergyTopologyService.refreshPending(level); }
    public static List<EnergyNetwork> getNetworks(LevelAccessor level, CableType type) {
        return EnergyTopologyService.networks(level, type);
    }
    /** Invalidates immediately and advances the shared budget; completion may take several ticks. */
    public static void updatePosition(LevelAccessor level, BlockPos pos) {
        invalidate(level, pos);
        refreshPending(level);
    }
}
