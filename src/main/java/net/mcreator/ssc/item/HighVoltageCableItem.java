package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.construction.CableCoilItem;
import net.ssc14.content.item.construction.ConstructionItemKind;

/** @deprecated Use the canonical high-voltage cable registry entry. */
@Deprecated(forRemoval = false)
public class HighVoltageCableItem extends CableCoilItem {
	public HighVoltageCableItem(Item.Properties properties) {
		super(properties, ConstructionItemKind.HIGH_VOLTAGE_CABLE);
	}
}
