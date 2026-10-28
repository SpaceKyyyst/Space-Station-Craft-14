package net.ssc14.content.gravity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class GravityGeneratorControlService {
    private static final String ACTIVE_KEY = "active";

    private GravityGeneratorControlService() {
    }

    public static void setActive(LevelAccessor level, BlockPos position, boolean active) {
        if (level.isClientSide()) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(position);
        if (blockEntity != null) {
            blockEntity.getPersistentData().putBoolean(ACTIVE_KEY, active);
        }
        if (level instanceof Level serverLevel) {
            var state = serverLevel.getBlockState(position);
            serverLevel.sendBlockUpdated(position, state, state, 3);
        }
    }
}
