package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.construction.CableCoilItem;
import net.ssc14.content.item.construction.ConstructionItemKind;

/** @deprecated Use the canonical low-voltage cable registry entry. */
@Deprecated(forRemoval = false)
public class LowVoltageCableItem extends CableCoilItem {
	public LowVoltageCableItem(Item.Properties properties) {
		super(properties, ConstructionItemKind.LOW_VOLTAGE_CABLE);
	}
}
