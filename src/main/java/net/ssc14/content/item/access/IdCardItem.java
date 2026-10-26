package net.ssc14.content.item.access;

import net.minecraft.world.item.Item;

/** Shared item contract for station identity and access cards. */
public class IdCardItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public IdCardItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
