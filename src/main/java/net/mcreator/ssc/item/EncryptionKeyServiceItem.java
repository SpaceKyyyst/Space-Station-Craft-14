package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyServiceItem extends Item {
	public EncryptionKeyServiceItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}