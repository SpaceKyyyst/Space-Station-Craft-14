package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.weapon.FoamDartLauncherService;

/** @deprecated Use {@link FoamDartLauncherService}. */
@Deprecated(forRemoval = false)
public final class AstroAceFP_DescriptionProcedure {
    private AstroAceFP_DescriptionProcedure() { }

    public static String execute(ItemStack itemstack) {
        return FoamDartLauncherService.description(itemstack);
    }
}
