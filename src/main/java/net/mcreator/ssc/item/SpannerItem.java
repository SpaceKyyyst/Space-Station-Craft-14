package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use the canonical spanner registration. */
@Deprecated(forRemoval = false)
public class SpannerItem extends MechanicalHandToolItem {
    public SpannerItem(Item.Properties properties) {
        super(properties, MechanicalToolProfile.SPANNER);
    }
}
