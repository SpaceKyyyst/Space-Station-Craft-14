package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.mcreator.ssc.AtmosphereManager;

public final class O2PercentTextProcedure {
    private O2PercentTextProcedure() { }
    public static String execute(LevelAccessor world, double x, double y, double z) {
        if (world == null) return "—";
        if (world.isClientSide()) return net.mcreator.ssc.client.GasAnalyserReadings.percent(0);
        if (world instanceof ServerLevel level) {
            var cell = AtmosphereManager.get(level).getCellAt(BlockPos.containing(x, y, z));
            if (cell != null) return String.format(java.util.Locale.ROOT, "%.1f", (double) (cell.getTotalMoles() > 0 ? 100 * cell.getMoles(0) / cell.getTotalMoles() : 0));
        }
        return "—";
    }
}
