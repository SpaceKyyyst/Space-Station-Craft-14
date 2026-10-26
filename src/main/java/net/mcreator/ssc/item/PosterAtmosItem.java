package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.poster.PosterItem;

/** @deprecated Use {@link PosterItem}. */
@Deprecated(forRemoval = false)
public class PosterAtmosItem extends PosterItem {
    public PosterAtmosItem(Item.Properties properties) {
        super(properties, "poster_atmos");
    }
}
