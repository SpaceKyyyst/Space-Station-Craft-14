package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawGoldItem extends Item {
	public RawGoldItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}