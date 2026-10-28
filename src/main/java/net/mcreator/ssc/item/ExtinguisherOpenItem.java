package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.safety.ExtinguisherState;

/** @deprecated Use {@link net.ssc14.content.item.safety.ExtinguisherItem}. */
@Deprecated(forRemoval = false)
public class ExtinguisherOpenItem extends net.ssc14.content.item.safety.ExtinguisherItem {
    public ExtinguisherOpenItem(Item.Properties properties) {
        super(properties, ExtinguisherState.OPEN);
    }
}
