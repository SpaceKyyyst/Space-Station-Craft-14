package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use the canonical screwdriver registration. */
@Deprecated(forRemoval = false)
public class ScrewdriverItem extends MechanicalHandToolItem {
    public ScrewdriverItem(Item.Properties properties) {
        super(properties, MechanicalToolProfile.SCREWDRIVER);
    }
}
