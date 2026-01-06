package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class SilverItem extends Item {
	public SilverItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}