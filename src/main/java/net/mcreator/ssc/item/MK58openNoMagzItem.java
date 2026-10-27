package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.weapon.Mk58Item;
import net.ssc14.content.item.weapon.Mk58State;

/** @deprecated Use the canonical MK-58 item registration. */
@Deprecated(forRemoval = false)
public class MK58openNoMagzItem extends Mk58Item {
    public MK58openNoMagzItem(Item.Properties properties) {
        super(properties, Mk58State.OPEN_NO_MAGAZINE);
    }
}
