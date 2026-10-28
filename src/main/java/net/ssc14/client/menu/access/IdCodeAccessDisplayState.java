package net.ssc14.client.menu.access;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.content.menu.access.IDcodeMenu;

/** Immutable per-frame view of the two ID-card slots. */
record IdCodeAccessDisplayState(boolean firstPresent, boolean secondPresent, CustomData sourceData) {
    static IdCodeAccessDisplayState capture(IDcodeMenu menu) {
        ItemStack first = stack(menu, 0);
        ItemStack second = stack(menu, 1);
        return fromStacks(first, second);
    }

    static IdCodeAccessDisplayState fromStacks(ItemStack first, ItemStack second) {
        return new IdCodeAccessDisplayState(!first.isEmpty(), !second.isEmpty(),
                first.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY));
    }

    boolean showFirstCardDecoration() { return firstPresent; }

    boolean showSingleCardDecoration() { return firstPresent != secondPresent; }

    boolean showTwoCardDecoration() { return firstPresent && secondPresent; }

    boolean locked(String accessKey) {
        return showTwoCardDecoration() && !sourceData.copyTag().getBooleanOr(accessKey, false);
    }

    private static ItemStack stack(IDcodeMenu menu, int slot) {
        var customSlot = menu.getSlots().get(slot);
        return customSlot == null ? ItemStack.EMPTY : customSlot.getItem();
    }
}
