package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;

/** @deprecated Use {@link PassiveEquipmentItem}. */
@Deprecated(forRemoval = false)
public class HardsuitSalvageHelmetItem extends PassiveEquipmentItem {
	public HardsuitSalvageHelmetItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
