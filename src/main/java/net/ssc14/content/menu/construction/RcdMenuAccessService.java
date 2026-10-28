package net.ssc14.content.menu.construction;

import net.minecraft.world.entity.player.Player;
import net.ssc14.content.item.Ssc14Items;

/** Keeps RCD menus open only while the player is holding the canonical RSU item. */
public final class RcdMenuAccessService {
    private RcdMenuAccessService() { }

	public static void closeIfToolMissing(Player player) {
		if (!player.getMainHandItem().is(Ssc14Items.RSU.get())) {
			player.closeContainer();
		}
	}
}
