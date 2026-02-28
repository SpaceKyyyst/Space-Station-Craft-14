package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawCoalItem extends Item {
	public RawCoalItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}