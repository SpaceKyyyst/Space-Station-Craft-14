package net.ssc14.content.item.machinery;

import net.minecraft.world.item.Item;

/** Shared behavior for inert machine parts and circuit boards. */
public class MachineComponentItem extends Item {
    public static final int BULK_STACK_SIZE = 30;
    public static final int SINGLE_STACK_SIZE = 1;

    public MachineComponentItem(Properties properties, int maxStackSize) {
        super(properties.stacksTo(maxStackSize));
    }
}
