package net.ssc14.content.item.food;

import net.minecraft.world.item.Item;

/** Canonical adapter for food-shaped items without a consumption contract. */
public class FoodPropItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public FoodPropItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
