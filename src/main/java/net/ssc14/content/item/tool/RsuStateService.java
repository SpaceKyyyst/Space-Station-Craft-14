package net.ssc14.content.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Owns the stable RSU item-data contract. */
public final class RsuStateService {
    public static final String CHARGES_KEY = "Charges";

    private RsuStateService() { }

    public static double charges(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(CHARGES_KEY, 0);
    }

    public static String tooltip(ItemStack stack) {
        return "Заряды: " + charges(stack);
    }
}
