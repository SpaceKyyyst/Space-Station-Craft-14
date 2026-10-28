package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.combat.AmmunitionItem;

/** @deprecated Use {@link AmmunitionItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class Bullet35CartridgeItem extends AmmunitionItem {
	public Bullet35CartridgeItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}
}
