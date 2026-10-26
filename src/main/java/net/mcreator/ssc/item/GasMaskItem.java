package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;

/** @deprecated Use {@link PassiveEquipmentItem}. */
@Deprecated(forRemoval = false)
public class GasMaskItem extends PassiveEquipmentItem {
	public GasMaskItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
