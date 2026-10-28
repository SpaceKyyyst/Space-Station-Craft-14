package net.ssc14.content.firearm;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class FirearmUiService {
    private FirearmUiService() { }

    public static boolean isMk58(Entity entity) {
        ItemStack held = entity instanceof LivingEntity living
                ? living.getMainHandItem() : ItemStack.EMPTY;
        return held.is(ItemTags.create(Identifier.parse("ssc14:mk58")));
    }
}
