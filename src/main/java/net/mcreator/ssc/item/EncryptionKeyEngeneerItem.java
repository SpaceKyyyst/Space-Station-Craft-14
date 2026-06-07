package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyEngeneerItem extends Item {
	public EncryptionKeyEngeneerItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}