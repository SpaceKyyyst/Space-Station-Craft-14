package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;

/** @deprecated Use {@link PassiveEquipmentItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class MantlesCapItem extends PassiveEquipmentItem {
	public MantlesCapItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
