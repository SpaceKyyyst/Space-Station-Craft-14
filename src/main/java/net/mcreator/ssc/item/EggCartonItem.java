package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

import net.ssc14.content.item.container.PortableMenuItem;
import net.ssc14.content.item.container.PortableMenuItemKind;

/** @deprecated Use the canonical portable-menu item registration. */
@Deprecated(forRemoval = false)
public class EggCartonItem extends PortableMenuItem {
	public EggCartonItem(Item.Properties properties) {
		super(properties, PortableMenuItemKind.EGG_CARTON);
	}
}
