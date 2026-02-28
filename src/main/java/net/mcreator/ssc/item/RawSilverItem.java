package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawSilverItem extends Item {
	public RawSilverItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}