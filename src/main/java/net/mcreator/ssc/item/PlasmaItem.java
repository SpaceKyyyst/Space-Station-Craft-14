package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class PlasmaItem extends Item {
	public PlasmaItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}