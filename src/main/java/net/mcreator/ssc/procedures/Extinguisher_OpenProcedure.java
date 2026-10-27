package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.safety.ExtinguisherUseService;

/** @deprecated Use {@link ExtinguisherUseService}. */
@Deprecated(forRemoval = false)
public class Extinguisher_OpenProcedure {
    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        ExtinguisherUseService.execute(world, entity, itemstack);
    }
}
