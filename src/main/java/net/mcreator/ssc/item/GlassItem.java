package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class GlassItem extends Item {
	public GlassItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}