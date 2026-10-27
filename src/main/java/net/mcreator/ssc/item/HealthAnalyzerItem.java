package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.utility.UtilityItem;

/** @deprecated Use {@link UtilityItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class HealthAnalyzerItem extends UtilityItem {
	public HealthAnalyzerItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}
}
