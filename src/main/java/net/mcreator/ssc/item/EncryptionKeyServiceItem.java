package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;

/** @deprecated Use {@link RadioEncryptionKeyItem}. */
@Deprecated(forRemoval = false)
public class EncryptionKeyServiceItem extends RadioEncryptionKeyItem {
	public EncryptionKeyServiceItem(Item.Properties properties) {
		super(properties);
	}
}
