package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.currency.CurrencyItem;
import net.ssc14.content.item.currency.CurrencyKind;

/** @deprecated Use the canonical fifty-credit registry entry. */
@Deprecated(forRemoval = false)
public class Credits50Item extends CurrencyItem {
    public Credits50Item(Item.Properties properties) {
        super(properties, CurrencyKind.CREDIT_50);
    }
}
