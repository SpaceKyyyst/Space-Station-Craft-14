package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.AirlockControllerService;

/** @deprecated Use {@link AirlockControllerService}. */
@Deprecated(forRemoval = false)
public final class AirlockController_Mode_SwitchingProcedure {
    private AirlockController_Mode_SwitchingProcedure() { }

    public static void execute(ItemStack itemstack) {
        AirlockControllerService.switchMode(itemstack);
    }
}
