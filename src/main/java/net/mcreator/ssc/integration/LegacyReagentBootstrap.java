package net.mcreator.ssc.integration;

import net.mcreator.ssc.ModReagents;
import net.neoforged.bus.api.IEventBus;

/** Compatibility bootstrap for the legacy reagent registry and fluids. */
public final class LegacyReagentBootstrap {
    private LegacyReagentBootstrap() {
    }

    public static void register(IEventBus modEventBus) {
        ModReagents.register(modEventBus);
    }
}
