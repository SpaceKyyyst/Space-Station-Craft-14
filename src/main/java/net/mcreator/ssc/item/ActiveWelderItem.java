package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.tool.WelderState;

/** @deprecated Use {@link net.ssc14.content.item.tool.WelderItem}. */
@Deprecated(forRemoval = false)
public class ActiveWelderItem extends net.ssc14.content.item.tool.WelderItem {
    public ActiveWelderItem(Item.Properties properties) {
        super(properties, WelderState.ACTIVE);
    }
}
