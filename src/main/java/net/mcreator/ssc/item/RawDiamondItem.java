package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawDiamondItem extends Item {
	public RawDiamondItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}