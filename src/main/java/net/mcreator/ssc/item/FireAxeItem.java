package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use the canonical fire-axe registration. */
@Deprecated(forRemoval = false)
public class FireAxeItem extends MechanicalHandToolItem {
    public FireAxeItem(Item.Properties properties) {
        super(properties, MechanicalToolProfile.FIRE_AXE);
    }
}
