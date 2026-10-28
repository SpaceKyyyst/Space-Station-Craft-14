package net.ssc14.content.atmosphere.runtime;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;
import net.ssc14.common.atmosphere.model.GasType;

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

    public static GasType gasByName(String name) {
        return GasType.byName(name);
    }

    public static String availableGasNames() {
        StringBuilder names = new StringBuilder();
        for (int index = 0; index < GasType.COUNT; index++) {
            GasType gas = GasType.byIndex(index);
            if (gas == null) {
                continue;
            }
            if (names.length() > 0) {
                names.append(", ");
            }
            names.append(gas.getName());
        }
        return names.toString();
    }
}
