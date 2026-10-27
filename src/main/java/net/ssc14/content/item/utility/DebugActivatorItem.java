package net.ssc14.content.item.utility;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/** Explicit debug-only activator marker with no runtime interaction behavior. */
public class DebugActivatorItem extends Item {
    public static final int MAX_STACK_SIZE = 1;
    public static final Rarity RARITY = Rarity.EPIC;

    public DebugActivatorItem(Properties properties) {
        super(properties.rarity(RARITY).stacksTo(MAX_STACK_SIZE));
    }
}
