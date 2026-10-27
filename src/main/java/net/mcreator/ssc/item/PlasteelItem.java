package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.construction.ConstructionItemKind;
import net.ssc14.content.item.construction.ConstructionSheetItem;

/** @deprecated Use the canonical plasteel construction-sheet registry entry. */
@Deprecated(forRemoval = false)
public class PlasteelItem extends ConstructionSheetItem {
	public PlasteelItem(Item.Properties properties) {
		super(properties, ConstructionItemKind.PLASTEEL_SHEET);
	}
}
