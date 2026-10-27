package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.currency.CurrencyItem;
import net.ssc14.content.item.currency.CurrencyKind;

/** @deprecated Use the canonical budget-deletion tool registry entry. */
@Deprecated(forRemoval = false)
public class DEBUGcreditsDELETERItem extends CurrencyItem {
    public DEBUGcreditsDELETERItem(Item.Properties properties) {
        super(properties, CurrencyKind.DEBUG_DELETER);
    }
}
