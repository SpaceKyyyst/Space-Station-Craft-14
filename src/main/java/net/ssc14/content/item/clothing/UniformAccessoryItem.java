package net.ssc14.content.item.clothing;

import net.minecraft.world.item.Item;

/** Shared behavior for Curios-compatible uniform accessories. */
public class UniformAccessoryItem extends Item {
    public UniformAccessoryItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
