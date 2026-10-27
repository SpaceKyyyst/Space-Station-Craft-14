package net.ssc14.content.item.utility;

import net.minecraft.world.item.Item;

/** Shared behavior for inert, fire-resistant utility tools. */
public class UtilityItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public UtilityItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
    }
}
