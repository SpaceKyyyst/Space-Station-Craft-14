package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.food.FoodPackagingWasteItem;

/** @deprecated Use {@link FoodPackagingWasteItem}. */
@Deprecated(forRemoval = false)
public class CheeseHonkersTrashItem extends FoodPackagingWasteItem {
	public CheeseHonkersTrashItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
