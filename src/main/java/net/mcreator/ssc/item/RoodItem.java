package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RoodItem extends Item {
	public RoodItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}