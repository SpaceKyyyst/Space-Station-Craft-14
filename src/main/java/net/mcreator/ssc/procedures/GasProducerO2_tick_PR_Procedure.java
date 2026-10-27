package net.mcreator.ssc.procedures;

import net.mcreator.ssc.AtmosCell;
import net.mcreator.ssc.AtmosphereManager;
import net.ssc14.common.atmosphere.model.GasType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public final class GasProducerO2_tick_PR_Procedure {
    private static final float MAX_MOLES_PER_CELL = 1000f;
    private static final float O2_PRODUCTION_RATE = 100f;

    private GasProducerO2_tick_PR_Procedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof ServerLevel level)) return;
        AtmosCell cell = AtmosphereManager.get(level).getCellAt(BlockPos.containing(x, y, z));
        if (cell == null) return;
        float capacity = Math.max(0f, MAX_MOLES_PER_CELL - cell.getTotalMoles());
        cell.addMoles(GasType.OXYGEN.ordinal(), Math.min(O2_PRODUCTION_RATE, capacity));
    }
}
