package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.Ssc14FoodDefinitions;

/** @deprecated Use {@link EdibleFoodItem}. */
@Deprecated(forRemoval = false)
public class TofuBurgerItem extends EdibleFoodItem {
	public TofuBurgerItem(Item.Properties properties) {
		super(properties, Ssc14FoodDefinitions.TOFU_BURGER);
	}
}
