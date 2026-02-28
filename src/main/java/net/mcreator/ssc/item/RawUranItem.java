package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawUranItem extends Item {
	public RawUranItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}