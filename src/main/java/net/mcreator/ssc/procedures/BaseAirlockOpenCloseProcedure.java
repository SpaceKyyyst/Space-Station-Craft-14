package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.airlock.AirlockLifecycleService;

/** Compatibility facade for generated airlock lifecycle callers. */
public final class BaseAirlockOpenCloseProcedure {
    private BaseAirlockOpenCloseProcedure() {
    }

    public static void execute(Level level, double x, double y, double z, BlockState state, Entity entity) {
        AirlockLifecycleService.execute(level, x, y, z, state, entity);
    }

    public static void tick(ServerLevel level, BlockPos pos) {
        AirlockLifecycleService.tick(level, pos);
    }

    public static void refreshAutoClose(ServerLevel level, BlockPos pos) {
        AirlockLifecycleService.refreshAutoClose(level, pos);
    }

    public static void requestSignal(ServerLevel level, BlockPos pos, boolean open, boolean toggle) {
        AirlockLifecycleService.requestSignal(level, pos, open, toggle);
    }

    public static void openManually(ServerLevel level, BlockPos pos) {
        AirlockLifecycleService.openManually(level, pos);
    }
}
