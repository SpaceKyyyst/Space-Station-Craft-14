package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class SSC14GoldItem extends Item {
	public SSC14GoldItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}