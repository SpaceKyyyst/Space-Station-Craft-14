package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.machinery.MachineComponentItem;

/** @deprecated Use {@link MachineComponentItem}. */
@Deprecated(forRemoval = false)
public class SMESBoardItem extends MachineComponentItem {
	public SMESBoardItem(Item.Properties properties) {
		super(properties, SINGLE_STACK_SIZE);
	}
}
