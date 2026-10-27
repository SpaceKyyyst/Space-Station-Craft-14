package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.ssc14.content.item.tool.NetworkConfiguratorService;

/** @deprecated Compatibility entry point for generated callers. */
@Deprecated(forRemoval = false)
public final class NetConfigGUI_CLOSE_Procedure {
    private NetConfigGUI_CLOSE_Procedure() { }

    public static void execute(Entity entity) {
        NetworkConfiguratorService.resetHeld(entity);
    }
}
