package net.ssc14.content.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Applies RCD mode selections from the GUI. */
public final class RcdModeService {
    private RcdModeService() { }

    public static void armWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 10));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void wall(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 2));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void directionalArmWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 9));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void deconstruct(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 1));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void catwalk(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 5));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void window(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 7));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void directionalWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 8));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void grille(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 6));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void heating(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 4));
        if (entity instanceof Player player) player.closeContainer();
    }
}
