package net.mcreator.ssc;

import net.minecraft.resources.Identifier;
import java.util.Map;

/** Capacities for registered reagent containers only. */
public final class ReagentContainerConfig {
    private ReagentContainerConfig() { }
    public static final Map<Identifier, Integer> CAPACITIES = Map.of(
        Identifier.fromNamespaceAndPath("ssc_14", "reagent_container"), 100,
        Identifier.fromNamespaceAndPath("ssc_14", "flour_open"), 50,
        Identifier.fromNamespaceAndPath("ssc_14", "sugar_open"), 50,
        Identifier.fromNamespaceAndPath("ssc_14", "milk_open"), 100,
        Identifier.fromNamespaceAndPath("ssc_14", "all_purpose_sauce_open"), 30
    );
}
