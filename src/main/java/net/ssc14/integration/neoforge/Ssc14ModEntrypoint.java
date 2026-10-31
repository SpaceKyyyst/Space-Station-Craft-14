package net.ssc14.integration.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.ssc14.integration.Ssc14Bootstrap;

/** NeoForge composition root for the Space Station Craft 14 mod. */
@Mod("ssc_14")
public final class Ssc14ModEntrypoint {
    public Ssc14ModEntrypoint(IEventBus modEventBus) {
        Ssc14Bootstrap.initialize(modEventBus);
    }
}
