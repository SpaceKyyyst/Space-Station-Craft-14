package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.combat.GlassShardKind;

/** @deprecated Use the canonical armored glass-shard registry entry. */
@Deprecated(forRemoval = false)
public class ArmGlassShardItem extends net.ssc14.content.item.combat.GlassShardItem {
    public ArmGlassShardItem(Item.Properties properties) {
        super(properties, GlassShardKind.ARMORED);
    }
}
