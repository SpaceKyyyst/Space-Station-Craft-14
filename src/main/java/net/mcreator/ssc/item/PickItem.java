package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.AsteroidPickItem;

/** @deprecated Use the canonical asteroid-pick registration. */
@Deprecated(forRemoval = false)
public class PickItem extends AsteroidPickItem {
    public PickItem(Item.Properties properties) {
        super(properties);
    }
}
