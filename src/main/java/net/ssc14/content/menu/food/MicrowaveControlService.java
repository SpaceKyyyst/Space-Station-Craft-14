package net.ssc14.content.menu.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.machinery.MicrowaveBlock;

/** Applies server-authoritative microwave timer controls. */
public final class MicrowaveControlService {
    private static final String TIMER_TAG = "timer";

    private MicrowaveControlService() { }

    public static boolean isActive(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.hasProperty(MicrowaveBlock.ACTIVE) && state.getValue(MicrowaveBlock.ACTIVE);
    }

    public static boolean setTimer(LevelAccessor world, BlockPos pos, int buttonId) {
        int seconds = MicrowaveButtonIds.timerSeconds(buttonId);
        if (seconds < 0 || world.isClientSide()) return false;

        BlockState state = world.getBlockState(pos);
        if (!state.hasProperty(MicrowaveBlock.ON_OFF)
                || !state.hasProperty(MicrowaveBlock.ACTIVE)
                || !state.getValue(MicrowaveBlock.ON_OFF)
                || state.getValue(MicrowaveBlock.ACTIVE)) {
            return false;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) return false;
        blockEntity.getPersistentData().putDouble(TIMER_TAG, seconds);
        blockEntity.setChanged();
        if (world instanceof Level level) level.sendBlockUpdated(pos, state, state, 3);
        return true;
    }
}
