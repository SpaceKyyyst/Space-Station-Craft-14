package net.mcreator.ssc.integration;

import net.mcreator.ssc.ModReagents;

import java.util.List;

/** Read-only compatibility view of the legacy reagent registry for client assets. */
public final class LegacyReagentCatalog {
    private LegacyReagentCatalog() {
    }

    public static List<ModReagents.Reagent> all() {
        return ModReagents.CONFIG_REAGENTS;
    }
}
