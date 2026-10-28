package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use the canonical nippers registration. */
@Deprecated(forRemoval = false)
public class NippersItem extends MechanicalHandToolItem {
    public NippersItem(Item.Properties properties) {
        super(properties, MechanicalToolProfile.NIPPERS);
    }
}
