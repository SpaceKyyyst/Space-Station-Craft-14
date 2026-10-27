package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.container.PortableMenuItem;
import net.ssc14.content.item.container.PortableMenuItemKind;

/** @deprecated Use the canonical portable-menu item registration. */
@Deprecated(forRemoval = true)
public class Backpack2Item extends PortableMenuItem {
	public Backpack2Item(Item.Properties properties) {
		super(properties, PortableMenuItemKind.BACKPACK);
	}
}
