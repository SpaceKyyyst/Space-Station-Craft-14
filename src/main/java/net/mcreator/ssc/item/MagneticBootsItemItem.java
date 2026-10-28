package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.equipment.MagneticBootsItem;
import net.ssc14.content.item.equipment.MagneticBootsState;

/** @deprecated Use {@link MagneticBootsItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class MagneticBootsItemItem extends MagneticBootsItem {
	public MagneticBootsItemItem(Item.Properties properties) {
		super(properties, MagneticBootsState.INACTIVE);
	}
}
