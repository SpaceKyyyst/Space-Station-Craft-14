package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeySecurityItem extends Item {
	public EncryptionKeySecurityItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}