package net.ssc14.content.explosive;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/** Handles C4 timer selection and activation without generated interaction code. */
public final class C4ActivationService {
    private C4ActivationService() { }

    public static void execute(Level level, BlockPos pos, Entity entity) {
        if (entity == null) return;
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;
        var data = blockEntity.getPersistentData();
        if (entity.isShiftKeyDown()) {
            if (data.getDoubleOr("c4timer", 0) != 0) return;
            double setting = data.getDoubleOr("c4setting", 0);
            double next = setting < 15 ? setting + 5 : setting < 30 ? setting + 15 : setting < 60 ? setting + 30 : 5;
            if (!level.isClientSide()) {
                data.putDouble("c4setting", next);
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            }
            if (entity instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("Настройка времени: " + next + " (сек.)"), true);
            }
            return;
        }
        if (data.getBooleanOr("activate", false)) return;
        if (!level.isClientSide()) {
            data.putBoolean("activate", true);
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            net.mcreator.ssc.procedures.C4explorePRProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
