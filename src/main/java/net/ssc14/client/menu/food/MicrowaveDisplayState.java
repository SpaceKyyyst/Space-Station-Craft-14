package net.ssc14.client.menu.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

/** Reads the microwave timer label once per rendered frame. */
final class MicrowaveDisplayState {
    private MicrowaveDisplayState() { }

    static String timerLabel(LevelAccessor world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) return "";
        double secondary = blockEntity.getPersistentData().getDoubleOr("timer_2", 0);
        double timer = blockEntity.getPersistentData().getDoubleOr("timer", 0);
        double displayed = secondary != 0 ? secondary : timer;
        return displayed == 0 ? "" : Math.round(Math.ceil(displayed)) + " с";
    }
}
