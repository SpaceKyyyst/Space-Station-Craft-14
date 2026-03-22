package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class LowBatterieItem extends Item {
	public LowBatterieItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}