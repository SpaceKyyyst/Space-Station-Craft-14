package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.ScarfItem;

/** @deprecated Use {@link ScarfItem}. */
@Deprecated(forRemoval = false)
public class BlackScarfItem extends ScarfItem {
	public BlackScarfItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
