package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class WoodItem extends Item {
	public WoodItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}