package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.weapon.Mk58State;

/** @deprecated Use the canonical MK-58 item registration. */
@Deprecated(forRemoval = false)
public class MK58Item extends net.ssc14.content.item.weapon.Mk58Item {
    public MK58Item(Item.Properties properties) {
        super(properties, Mk58State.CLOSED_WITH_MAGAZINE);
    }
}
