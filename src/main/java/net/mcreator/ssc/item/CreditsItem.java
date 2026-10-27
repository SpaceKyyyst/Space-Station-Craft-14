package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.currency.CurrencyItem;
import net.ssc14.content.item.currency.CurrencyKind;

/** @deprecated Use the canonical single-credit registry entry. */
@Deprecated(forRemoval = false)
public class CreditsItem extends CurrencyItem {
    public CreditsItem(Item.Properties properties) {
        super(properties, CurrencyKind.CREDIT);
    }
}
