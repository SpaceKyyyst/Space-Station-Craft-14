package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class CapacitorItem extends Item {
	public CapacitorItem(Item.Properties properties) {
		super(properties.stacksTo(30));
	}
}