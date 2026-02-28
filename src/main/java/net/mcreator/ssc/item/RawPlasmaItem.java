package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class RawPlasmaItem extends Item {
	public RawPlasmaItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}