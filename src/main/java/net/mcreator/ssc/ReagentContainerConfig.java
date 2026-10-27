package net.mcreator.ssc;

import java.util.Map;
import net.minecraft.resources.Identifier;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

/** @deprecated Use {@link Ssc14ReagentContainers}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public final class ReagentContainerConfig {
    private ReagentContainerConfig() { }

    public static final Map<Identifier, Integer> CAPACITIES =
            Ssc14ReagentContainers.CAPACITIES;
}
