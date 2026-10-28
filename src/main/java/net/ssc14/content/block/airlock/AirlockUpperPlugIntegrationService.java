package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.ssc14.content.airlock.AirlockUpperPlugDestroyService;
import net.ssc14.content.airlock.AirlockUpperPlugInteractionService;
import net.ssc14.content.airlock.AirlockUpperPlugValidationService;

/** Integration boundary between canonical upper airlock plugs and legacy procedures. */
public final class AirlockUpperPlugIntegrationService {
    @FunctionalInterface
    public interface UseHandler {
        void use(Level level, BlockPos pos, Player player);
    }

    private static UseHandler useHandler = AirlockUpperPlugInteractionService::execute;

    private AirlockUpperPlugIntegrationService() { }

    public static void installUseHandler(UseHandler replacement) {
        useHandler = java.util.Objects.requireNonNull(replacement, "replacement");
    }

    public static void update(Level level, BlockPos pos) {
        AirlockUpperPlugValidationService.execute(level, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void destroy(Level level, BlockPos pos) {
        AirlockUpperPlugDestroyService.execute(level, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void use(Level level, BlockPos pos, Player player) {
        useHandler.use(level, pos, player);
    }
}
