package net.mcreator.ssc.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class DEBUGACTIVATORItem extends Item {
	public DEBUGACTIVATORItem(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC).stacksTo(1));
	}
}