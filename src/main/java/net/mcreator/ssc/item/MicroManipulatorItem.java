package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class MicroManipulatorItem extends Item {
	public MicroManipulatorItem(Item.Properties properties) {
		super(properties.stacksTo(30));
	}
}