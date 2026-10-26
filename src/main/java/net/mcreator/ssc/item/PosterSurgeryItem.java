package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.poster.PosterItem;

/** @deprecated Use {@link PosterItem}. */
@Deprecated(forRemoval = false)
public class PosterSurgeryItem extends PosterItem {
    public PosterSurgeryItem(Item.Properties properties) {
        super(properties, "poster_surgery");
    }
}
