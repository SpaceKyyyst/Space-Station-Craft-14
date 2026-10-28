package net.ssc14.content.item.utility;

import net.minecraft.world.item.Item;

/** Consumable cartridge used by rapid construction devices. */
public class RcdCartridgeItem extends Item {
    public static final int MAX_STACK_SIZE = 60;

    public RcdCartridgeItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
