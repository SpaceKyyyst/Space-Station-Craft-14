package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.combat.GlassShardKind;

/** @deprecated Use the canonical standard glass-shard registry entry. */
@Deprecated(forRemoval = false)
public class GlassShardItem extends net.ssc14.content.item.combat.GlassShardItem {
    public GlassShardItem(Item.Properties properties) {
        super(properties, GlassShardKind.STANDARD);
    }
}
