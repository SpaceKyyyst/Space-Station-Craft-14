package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.construction.ConstructionItemKind;
import net.ssc14.content.item.construction.ConstructionSheetItem;

/** @deprecated Use the canonical steel construction-sheet registry entry. */
@Deprecated(forRemoval = false)
public class SteelItem extends ConstructionSheetItem {
	public SteelItem(Item.Properties properties) {
		super(properties, ConstructionItemKind.STEEL_SHEET);
	}
}
