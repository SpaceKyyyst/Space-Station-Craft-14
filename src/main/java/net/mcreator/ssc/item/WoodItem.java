package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.material.MaterialItem;

/** @deprecated Use {@link MaterialItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class WoodItem extends MaterialItem {
	public WoodItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}
