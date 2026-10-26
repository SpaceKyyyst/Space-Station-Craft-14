package net.ssc14.content.item.food;

import net.minecraft.world.item.Item;

/** Shared behavior for passive cooking ingredients. */
public class CookingIngredientItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public CookingIngredientItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
