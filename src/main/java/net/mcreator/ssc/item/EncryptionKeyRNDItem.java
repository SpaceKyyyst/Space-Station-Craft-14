package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyRNDItem extends Item {
	public EncryptionKeyRNDItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}