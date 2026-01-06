package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class PlasticItem extends Item {
	public PlasticItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}