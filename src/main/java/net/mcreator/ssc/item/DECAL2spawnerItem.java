package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

/** @deprecated Use the canonical decal-two spawner registry entry. */
@Deprecated(forRemoval = false)
public class DECAL2spawnerItem extends DecalToolItem {
    public DECAL2spawnerItem(Item.Properties properties) {
        super(properties, DecalToolKind.SPAWNER_2);
    }
}
