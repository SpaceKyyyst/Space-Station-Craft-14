package net.ssc14.common.chemistry;


import java.util.List;
import java.util.Optional;

/** Read-only compatibility view of the legacy reagent registry for client assets. */
public final class ReagentCatalog {
    private ReagentCatalog() {
    }

    public static List<ReagentService.Reagent> all() {
        return ReagentService.CONFIG_REAGENTS;
    }

    public static Optional<ReagentService.Reagent> find(String id) {
        return ReagentService.getReagent(id);
    }
}
