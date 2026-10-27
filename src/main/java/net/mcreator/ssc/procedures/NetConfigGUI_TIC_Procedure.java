package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.tool.NetworkConfiguratorService;

/** @deprecated Compatibility entry point for generated callers. */
@Deprecated(forRemoval = false)
public final class NetConfigGUI_TIC_Procedure {
    private NetConfigGUI_TIC_Procedure() { }

    public static void execute(LevelAccessor world, Entity entity) {
        NetworkConfiguratorService.closeWhenNotHeld(entity);
    }
}
