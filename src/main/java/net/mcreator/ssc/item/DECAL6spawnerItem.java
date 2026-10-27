package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

/** @deprecated Use the canonical decal-six spawner registry entry. */
@Deprecated(forRemoval = false)
public class DECAL6spawnerItem extends DecalToolItem {
    public DECAL6spawnerItem(Item.Properties properties) {
        super(properties, DecalToolKind.SPAWNER_6);
    }
}
