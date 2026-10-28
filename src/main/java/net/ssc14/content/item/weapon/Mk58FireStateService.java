package net.ssc14.content.item.weapon;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.player.Player;

/** Canonical access to persisted MK-58 firing state. */
public final class Mk58FireStateService {
    public static final String BULLET_CHAMBER = "BulletChamber";
    public static final String MAGAZINE_BULLETS = "MagazBullets";

    private Mk58FireStateService() { }

    public static double chamber(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(BULLET_CHAMBER, 0);
    }

    public static double magazine(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(MAGAZINE_BULLETS, 0);
    }

    public static boolean hasChamberedRound(ItemStack stack) {
        return chamber(stack) > 0;
    }

    public static boolean hasMagazineRound(ItemStack stack) {
        return magazine(stack) > 0;
    }

    public static void setChamber(ItemStack stack, double value) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(BULLET_CHAMBER, Math.max(0, value)));
    }

    public static void consumeMagazineRound(ItemStack stack) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(MAGAZINE_BULLETS, Math.max(0, magazine(stack) - 1)));
    }

    public static boolean isCoolingDown(Player player, ItemStack stack) {
        return player.getCooldowns().isOnCooldown(stack);
    }

    public static void startCooldown(Player player, ItemStack stack, int ticks) {
        player.getCooldowns().addCooldown(stack, ticks);
    }
}
