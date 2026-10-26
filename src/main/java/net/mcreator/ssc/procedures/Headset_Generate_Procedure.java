package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.communication.HeadsetInitializationService;

/** @deprecated Use {@link HeadsetInitializationService}. */
@Deprecated(forRemoval = false)
public class Headset_Generate_Procedure {
    public static void execute(ItemStack itemStack) {
        HeadsetInitializationService.initialize(itemStack);
    }
}
