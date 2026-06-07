package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyMedicalItem extends Item {
	public EncryptionKeyMedicalItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}