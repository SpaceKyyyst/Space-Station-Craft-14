package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.NetworkConfiguratorService;

/** @deprecated Compatibility entry point for generated callers. */
@Deprecated(forRemoval = false)
public final class NetworkConfigurator_DROP_Procedure {
    private NetworkConfigurator_DROP_Procedure() { }

    public static void execute(ItemStack itemstack) {
        NetworkConfiguratorService.reset(itemstack);
    }
}
