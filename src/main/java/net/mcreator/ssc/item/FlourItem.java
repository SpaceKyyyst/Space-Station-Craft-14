package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.reagent.SealedReagentContainerItem;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

/** @deprecated Use the canonical sealed reagent container registry entry. */
@Deprecated(forRemoval = false)
public class FlourItem extends SealedReagentContainerItem {
	public FlourItem(Item.Properties properties) {
		super(properties, Ssc14ReagentContainers.FLOUR, Ssc14Items.FLOUR_OPEN);
	}
}
