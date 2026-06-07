package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class EncryptionKeyPassangerItem extends Item {
	public EncryptionKeyPassangerItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}