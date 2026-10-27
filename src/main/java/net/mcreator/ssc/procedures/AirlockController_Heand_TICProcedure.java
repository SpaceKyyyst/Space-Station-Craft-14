package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.AirlockControllerService;

/** @deprecated Use {@link AirlockControllerService}. */
@Deprecated(forRemoval = false)
public final class AirlockController_Heand_TICProcedure {
    private AirlockController_Heand_TICProcedure() { }

    public static void execute(Entity entity, ItemStack itemstack) {
        AirlockControllerService.showMode(entity, itemstack);
    }
}
