package net.ssc14.content.item.material;

import net.minecraft.world.item.Item;

/** Shared behavior for inert, fire-resistant material stacks. */
public class MaterialItem extends Item {
    public static final int MAX_STACK_SIZE = 30;

    public MaterialItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE).fireResistant());
    }
}
