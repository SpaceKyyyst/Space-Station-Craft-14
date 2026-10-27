package net.ssc14.content.item.combat;

import net.minecraft.world.item.Item;

/** Shared behavior for inert single-stack ammunition and cartridges. */
public class AmmunitionItem extends Item {
    public static final int MAX_STACK_SIZE = 1;

    public AmmunitionItem(Properties properties) {
        super(properties.stacksTo(MAX_STACK_SIZE));
    }
}
