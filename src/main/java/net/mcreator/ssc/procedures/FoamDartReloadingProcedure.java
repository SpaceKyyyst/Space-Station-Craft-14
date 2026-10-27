package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.weapon.FoamDartLauncherService;

/** @deprecated Use {@link FoamDartLauncherService}. */
@Deprecated(forRemoval = false)
public final class FoamDartReloadingProcedure {
    private FoamDartReloadingProcedure() { }

    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        FoamDartLauncherService.toggleDart(world, entity, itemstack);
    }
}
