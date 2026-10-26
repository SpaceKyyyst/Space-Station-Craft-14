package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.communication.HeadsetItem;
import net.ssc14.content.item.communication.Ssc14HeadsetDefinitions;

/** @deprecated Use {@link HeadsetItem}; retained for source compatibility. */
@Deprecated(forRemoval = false)
public class HeadsetSyndicateItem extends HeadsetItem {
	public HeadsetSyndicateItem(Item.Properties properties) {
		super(properties, Ssc14HeadsetDefinitions.SYNDICATE);
	}
}
