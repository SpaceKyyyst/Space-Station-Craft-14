package net.ssc14.content.firearm;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class FirearmUiService {
    private FirearmUiService() { }

    public static boolean isMk58(Entity entity) {
        ItemStack held = entity instanceof LivingEntity living
                ? living.getMainHandItem() : ItemStack.EMPTY;
        return held.is(ItemTags.create(Identifier.parse("ssc14:mk58")));
    }

    public static double magazineBullets(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return 0;
        ItemStack stack = living.getMainHandItem();
        if (!stack.is(ItemTags.create(Identifier.parse("ssc14:mk58")))) return 0;
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("MagazBullets", 0);
    }

    public static boolean chamberLoaded(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return false;
        ItemStack stack = living.getMainHandItem();
        return stack.is(ItemTags.create(Identifier.parse("ssc14:mk58")))
                && stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("BulletChamber", 0) == 1;
    }
}
