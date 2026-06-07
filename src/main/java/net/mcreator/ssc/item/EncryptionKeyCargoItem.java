package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyCargoItem extends Item {
	public EncryptionKeyCargoItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}