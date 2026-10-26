package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.food.FoodPackagingWasteItem;

/** @deprecated Use {@link FoodPackagingWasteItem}. */
@Deprecated(forRemoval = false)
public class PopcornTrashItem extends FoodPackagingWasteItem {
	public PopcornTrashItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
