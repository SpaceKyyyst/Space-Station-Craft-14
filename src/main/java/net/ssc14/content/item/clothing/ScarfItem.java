package net.ssc14.content.item.clothing;

import net.minecraft.world.item.Item;

/** Shared behavior for Curios-compatible scarves. */
public class ScarfItem extends Item {
    public ScarfItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
