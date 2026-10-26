package net.ssc14.content.item.lighting;

import net.minecraft.world.item.Item;

/** Shared behavior for passive lighting replacement parts. */
public class LightingComponentItem extends Item {
    public LightingComponentItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
