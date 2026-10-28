package net.ssc14.content.item.food;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;

/** Immutable gameplay contract for one edible item. */
public record FoodDefinition(
        int maxBites,
        String tasteMessage,
        Supplier<? extends Item> trash) {

    public FoodDefinition {
        if (maxBites < 1) {
            throw new IllegalArgumentException("Food must provide at least one bite");
        }
        Objects.requireNonNull(tasteMessage, "Taste message");
    }

    public static FoodDefinition withoutTrash(int maxBites, String tasteMessage) {
        return new FoodDefinition(maxBites, tasteMessage, null);
    }

    public static FoodDefinition withTrash(int maxBites, String tasteMessage,
            Supplier<? extends Item> trash) {
        return new FoodDefinition(
                maxBites, tasteMessage, Objects.requireNonNull(trash, "Trash supplier"));
    }
}
