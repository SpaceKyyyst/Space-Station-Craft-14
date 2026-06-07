package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyCommandItem extends Item {
	public EncryptionKeyCommandItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}