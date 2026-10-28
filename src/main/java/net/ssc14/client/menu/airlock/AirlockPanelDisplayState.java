package net.ssc14.client.menu.airlock;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ssc14.client.airlock.AirlockPanelIndicators;

/** One block-entity snapshot shared by all wire and indicator draws in a frame. */
record AirlockPanelDisplayState(Set<String> cutWires, Set<String> litIndicators) {
    private static final String[] INDICATORS = {
        "powered", "bolted", "timer", "ai_access", "logs", "diods", "safe"
    };

    static AirlockPanelDisplayState empty() {
        return new AirlockPanelDisplayState(Set.of(), Set.of());
    }

    static AirlockPanelDisplayState read(Level level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) {
            return empty();
        }
        var data = blockEntity.getPersistentData();
        var wires = new HashSet<String>();
        for (int wire = 1; wire <= 8; wire++) {
            String key = "cabel_" + wire;
            if (data.getBooleanOr(key, false)) {
                wires.add(key);
            }
        }
        var indicators = new HashSet<String>();
        for (String indicator : INDICATORS) {
            if (AirlockPanelIndicators.lit(data, indicator, level.getGameTime())) {
                indicators.add(indicator);
            }
        }
        return new AirlockPanelDisplayState(Set.copyOf(wires), Set.copyOf(indicators));
    }

    boolean panelFlag(String key) {
        return cutWires.contains(key);
    }

    boolean indicatorLit(String key) {
        return litIndicators.contains(key);
    }
}
