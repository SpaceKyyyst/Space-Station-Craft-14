package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.machinery.MachineComponentItem;

/** @deprecated Use {@link MachineComponentItem}. */
@Deprecated(forRemoval = false)
public class CapacitorItem extends MachineComponentItem {
	public CapacitorItem(Item.Properties properties) {
		super(properties, BULK_STACK_SIZE);
	}
}
