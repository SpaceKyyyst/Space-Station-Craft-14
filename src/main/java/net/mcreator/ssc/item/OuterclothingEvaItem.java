package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;

/** @deprecated Use {@link PassiveEquipmentItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class OuterclothingEvaItem extends PassiveEquipmentItem {
	public OuterclothingEvaItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
