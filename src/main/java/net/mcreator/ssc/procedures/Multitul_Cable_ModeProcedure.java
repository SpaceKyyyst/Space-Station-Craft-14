package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.MultitoolModeService;

/** @deprecated Use {@link MultitoolModeService}. */
@Deprecated(forRemoval = false)
public final class Multitul_Cable_ModeProcedure {
    private Multitul_Cable_ModeProcedure() { }

    public static void execute(Entity entity, ItemStack itemstack) {
        MultitoolModeService.switchMode(entity, itemstack);
    }
}
