package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.UniformAccessoryItem;

/** @deprecated Use {@link UniformAccessoryItem}. */
@Deprecated(forRemoval = false)
public class StockingsLPinkItem extends UniformAccessoryItem {
	public StockingsLPinkItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
