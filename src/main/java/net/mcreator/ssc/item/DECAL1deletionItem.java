package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

/** @deprecated Use the canonical decal deletion registry entry. */
@Deprecated(forRemoval = false)
public class DECAL1deletionItem extends DecalToolItem {
    public DECAL1deletionItem(Item.Properties properties) {
        super(properties, DecalToolKind.DELETION);
    }
}
