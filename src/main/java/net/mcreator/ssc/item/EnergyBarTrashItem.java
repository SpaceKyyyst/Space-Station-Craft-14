package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.food.FoodPackagingWasteItem;

/** @deprecated Use {@link FoodPackagingWasteItem}. */
@Deprecated(forRemoval = false)
public class EnergyBarTrashItem extends FoodPackagingWasteItem {
	public EnergyBarTrashItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
