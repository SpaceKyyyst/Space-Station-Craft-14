package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;

public class HealthAnalyzerItem extends Item {
	public HealthAnalyzerItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}