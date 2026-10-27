package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.WelderStateService;

/** @deprecated Use {@link WelderStateService}. */
@Deprecated(forRemoval = false)
public class ActiveWelder_DROP_Procedure {
    public static void execute(LevelAccessor world, double x, double y, double z,
            ItemStack itemstack) {
        WelderStateService.dropInactive(world, x, y, z, itemstack);
    }
}
