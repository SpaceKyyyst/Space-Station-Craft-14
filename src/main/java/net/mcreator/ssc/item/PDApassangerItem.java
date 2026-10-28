package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

import net.ssc14.content.item.container.PortableMenuItem;
import net.ssc14.content.item.container.PortableMenuItemKind;

/** @deprecated Use the canonical portable-menu item registration. */
@Deprecated(forRemoval = false)
public class PDApassangerItem extends PortableMenuItem {
	public PDApassangerItem(Item.Properties properties) {
		super(properties, PortableMenuItemKind.PASSENGER_PDA);
	}
}
