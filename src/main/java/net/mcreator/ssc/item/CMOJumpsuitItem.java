package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.UniformJumpsuitItem;

/** @deprecated Use {@link UniformJumpsuitItem}. */
@Deprecated(forRemoval = false)
public class CMOJumpsuitItem extends UniformJumpsuitItem {
	public CMOJumpsuitItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
