package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.medical.MedicalSupplyItem;

/** @deprecated Use {@link MedicalSupplyItem}. */
@Deprecated(forRemoval = false)
public class GauzeItem extends MedicalSupplyItem {
	public GauzeItem(Item.Properties properties) {
		super(properties.stacksTo(30).fireResistant());
	}
}
