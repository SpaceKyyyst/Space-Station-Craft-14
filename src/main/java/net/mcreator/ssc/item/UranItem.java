package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class UranItem extends Item {
	public UranItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}