package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.furniture.TableConnectionService;

/** @deprecated Table blocks invoke {@link TableConnectionService} directly. */
@Deprecated(forRemoval = false)
public final class SteelTableStateUpdateProcedure {
    private SteelTableStateUpdateProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {
        TableConnectionService.refresh(world, x, y, z);
    }
}
