package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

/** @deprecated Use the canonical decal-four spawner registry entry. */
@Deprecated(forRemoval = false)
public class DECAL4spawnerItem extends DecalToolItem {
    public DECAL4spawnerItem(Item.Properties properties) {
        super(properties, DecalToolKind.SPAWNER_4);
    }
}
