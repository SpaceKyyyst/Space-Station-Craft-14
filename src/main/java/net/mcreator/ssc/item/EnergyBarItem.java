package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.food.PackagedEnergyBarItem;

/** @deprecated Use {@link PackagedEnergyBarItem}; retained for binary compatibility. */
@Deprecated(forRemoval = false)
public class EnergyBarItem extends PackagedEnergyBarItem {
	public EnergyBarItem(Item.Properties properties) {
		super(properties);
	}
}
