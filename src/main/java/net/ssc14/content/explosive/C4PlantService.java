package net.ssc14.content.explosive;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** Applies the initial armed-state marker to a C4 charge. */
public final class C4PlantService {
    private C4PlantService() { }

    public static void execute(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;
        var state = level.getBlockState(pos);
        blockEntity.getPersistentData().putDouble("c4setting", 5);
        level.sendBlockUpdated(pos, state, state, 3);
    }
}
