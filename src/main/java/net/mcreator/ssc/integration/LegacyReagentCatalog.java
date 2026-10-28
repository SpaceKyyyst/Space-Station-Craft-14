package net.mcreator.ssc.integration;

import net.mcreator.ssc.ModReagents;

import java.util.List;
import java.util.Optional;

/** Read-only compatibility view of the legacy reagent registry for client assets. */
public final class LegacyReagentCatalog {
    private LegacyReagentCatalog() {
    }

    public static List<ModReagents.Reagent> all() {
        return ModReagents.CONFIG_REAGENTS;
    }

    public static Optional<ModReagents.Reagent> find(String id) {
        return ModReagents.getReagent(id);
    }
}
