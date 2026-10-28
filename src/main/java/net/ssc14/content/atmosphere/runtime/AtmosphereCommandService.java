package net.ssc14.content.atmosphere.runtime;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;

public final class AtmosphereCommandService {
    private AtmosphereCommandService() { }

    public static AtmosCell cellAt(ServerLevel level, BlockPos position) {
        return AtmosphereManager.get(level).getCellAt(position);
    }

    public static float setTemperature(AtmosCell cell, double temperature) {
        float previous = cell.getTemperature();
        cell.setTemperature((float) temperature);
        return previous;
    }

    public static void addGas(AtmosCell cell, int gasIndex, double amount) {
        cell.addMoles(gasIndex, (float) amount);
    }
}
