package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.NetworkConfiguratorService;

/** @deprecated Compatibility entry point for generated callers. */
@Deprecated(forRemoval = false)
public final class NetworkConfigurator_USE_Procedure {
    private NetworkConfigurator_USE_Procedure() { }

    public static void execute(LevelAccessor world, double x, double y, double z,
            Entity entity, ItemStack itemstack) {
        NetworkConfiguratorService.use(world, BlockPos.containing(x, y, z),
                entity, itemstack);
    }
}
