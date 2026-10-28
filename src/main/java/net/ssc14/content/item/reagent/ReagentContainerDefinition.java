package net.ssc14.content.item.reagent;

import java.util.Objects;
import net.minecraft.resources.Identifier;

/** Immutable opening contract for one sealed reagent container. */
public record ReagentContainerDefinition(
        String reagentId, int fillAmount, Identifier openingSoundId) {
    public ReagentContainerDefinition {
        if (reagentId == null || reagentId.isBlank()) {
            throw new IllegalArgumentException("reagentId must not be blank");
        }
        if (fillAmount <= 0) {
            throw new IllegalArgumentException("fillAmount must be positive");
        }
        Objects.requireNonNull(openingSoundId, "openingSoundId");
    }
}
