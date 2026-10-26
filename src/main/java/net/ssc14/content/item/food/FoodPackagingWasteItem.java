package net.ssc14.content.item.food;

import net.minecraft.world.item.Item;

/** Shared behavior for empty food packaging produced after consumption. */
public class FoodPackagingWasteItem extends Item {
    public FoodPackagingWasteItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
