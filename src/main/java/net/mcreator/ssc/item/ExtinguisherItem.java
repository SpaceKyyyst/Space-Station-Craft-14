package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.safety.ExtinguisherState;

/** @deprecated Use {@link net.ssc14.content.item.safety.ExtinguisherItem}. */
@Deprecated(forRemoval = false)
public class ExtinguisherItem extends net.ssc14.content.item.safety.ExtinguisherItem {
    public ExtinguisherItem(Item.Properties properties) {
        super(properties, ExtinguisherState.CLOSED);
    }
}
