package net.ssc14.content.item.utility;

import net.minecraft.world.item.Item;

/** Single-stack portable traffic marker. */
public class TrafficConeItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public TrafficConeItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
