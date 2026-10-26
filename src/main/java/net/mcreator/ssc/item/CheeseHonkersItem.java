package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.Ssc14FoodDefinitions;

/** @deprecated Use {@link EdibleFoodItem}. */
@Deprecated(forRemoval = false)
public class CheeseHonkersItem extends EdibleFoodItem {
	public CheeseHonkersItem(Item.Properties properties) {
		super(properties, Ssc14FoodDefinitions.cheeseHonkers(
				Ssc14Items.CHEESE_HONKERS_TRASH));
	}
}
