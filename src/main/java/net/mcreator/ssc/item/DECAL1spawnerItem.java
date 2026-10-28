package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

/** @deprecated Use the canonical decal-one spawner registry entry. */
@Deprecated(forRemoval = false)
public class DECAL1spawnerItem extends DecalToolItem {
    public DECAL1spawnerItem(Item.Properties properties) {
        super(properties, DecalToolKind.SPAWNER_1);
    }
}
