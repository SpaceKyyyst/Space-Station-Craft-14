package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class MatterBinItem extends Item {
	public MatterBinItem(Item.Properties properties) {
		super(properties.stacksTo(30));
	}
}