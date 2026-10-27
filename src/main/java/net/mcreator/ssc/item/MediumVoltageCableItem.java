package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.construction.CableCoilItem;
import net.ssc14.content.item.construction.ConstructionItemKind;

/** @deprecated Use the canonical medium-voltage cable registry entry. */
@Deprecated(forRemoval = false)
public class MediumVoltageCableItem extends CableCoilItem {
	public MediumVoltageCableItem(Item.Properties properties) {
		super(properties, ConstructionItemKind.MEDIUM_VOLTAGE_CABLE);
	}
}
