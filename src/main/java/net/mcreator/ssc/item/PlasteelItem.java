package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class PlasteelItem extends Item {
	public PlasteelItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}