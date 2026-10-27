package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

/** @deprecated Use the canonical crowbar registration. */
@Deprecated(forRemoval = false)
public class ScrapItem extends MechanicalHandToolItem {
    public ScrapItem(Item.Properties properties) {
        super(properties, MechanicalToolProfile.CROWBAR);
    }
}
