package net.ssc14.content.menu.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

/** Reads the six persisted vending-stock indicators without generated procedures. */
public final class VendingIndicatorState {
    private static final int TEXTURE_STEP = 5;
    private static final int MAX_TEXTURE_OFFSET = 95;

    private VendingIndicatorState() { }

    public static double read(LevelAccessor level, BlockPos vendingPos, int slotNumber) {
        BlockEntity blockEntity = level.getBlockEntity(vendingPos);
        if (blockEntity == null) {
            return -1;
        }
        return blockEntity.getPersistentData().getDoubleOr(slotKey(slotNumber), 0);
    }

    public static int textureOffset(double amount) {
        return Mth.clamp((int) amount * TEXTURE_STEP, 0, MAX_TEXTURE_OFFSET);
    }

    static String slotKey(int slotNumber) {
        return "slot_" + slotNumber;
    }
}
