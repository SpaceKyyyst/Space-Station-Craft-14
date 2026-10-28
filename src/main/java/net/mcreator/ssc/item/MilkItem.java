package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.Ssc14Items;
import net.ssc14.content.item.reagent.SealedReagentContainerItem;
import net.ssc14.content.item.reagent.Ssc14ReagentContainers;

/** @deprecated Use the canonical sealed reagent container registry entry. */
@Deprecated(forRemoval = false)
public class MilkItem extends SealedReagentContainerItem {
	public MilkItem(Item.Properties properties) {
		super(properties, Ssc14ReagentContainers.MILK, Ssc14Items.MILK_OPEN);
	}
}
