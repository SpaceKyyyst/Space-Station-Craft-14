package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.poster.PosterItem;

/** @deprecated Use {@link PosterItem}. */
@Deprecated(forRemoval = false)
public class PosterTelecomsItem extends PosterItem {
    public PosterTelecomsItem(Item.Properties properties) {
        super(properties, "poster_telecoms");
    }
}
