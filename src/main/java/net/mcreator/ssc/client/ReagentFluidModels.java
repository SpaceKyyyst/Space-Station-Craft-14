package net.mcreator.ssc.client;

import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

/** @deprecated Use the canonical reagent fluid model registration. */
@Deprecated(forRemoval = false)
public final class ReagentFluidModels {
    private ReagentFluidModels() { }
    public static void register(RegisterFluidModelsEvent event) {
        net.ssc14.client.reagent.ReagentFluidModels.register(event);
    }
}
