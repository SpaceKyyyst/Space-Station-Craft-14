package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.clothing.UniformJumpsuitItem;

/** @deprecated Use {@link UniformJumpsuitItem}. */
@Deprecated(forRemoval = false)
public class BrigmedicJumpsuitItem extends UniformJumpsuitItem {
	public BrigmedicJumpsuitItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
