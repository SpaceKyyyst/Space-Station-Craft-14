package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.MultitoolModeService;

/** @deprecated Use {@link MultitoolModeService}. */
@Deprecated(forRemoval = false)
public final class Multitul_DescriptionProcedure {
    private Multitul_DescriptionProcedure() { }

    public static String execute(ItemStack itemstack) {
        return MultitoolModeService.description(itemstack);
    }
}
