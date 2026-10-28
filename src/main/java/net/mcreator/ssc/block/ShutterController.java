package net.mcreator.ssc.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.airlock.ShuttersAnimationService;

/** @deprecated Use {@link ShuttersAnimationService}. */
@Deprecated(forRemoval = false)
public final class ShutterController {
    private ShutterController() { }

    public static void setOpen(LevelAccessor accessor, BlockPos position, boolean open) {
        ShuttersAnimationService.setOpen(accessor, position, open);
    }
}
