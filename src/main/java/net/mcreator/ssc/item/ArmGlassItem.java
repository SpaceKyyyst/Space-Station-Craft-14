package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class ArmGlassItem extends Item {
	public ArmGlassItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}