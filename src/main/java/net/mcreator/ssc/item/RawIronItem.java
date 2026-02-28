package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawIronItem extends Item {
	public RawIronItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}