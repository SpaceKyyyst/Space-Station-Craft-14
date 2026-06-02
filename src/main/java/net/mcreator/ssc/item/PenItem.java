package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class PenItem extends Item {
	public PenItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}