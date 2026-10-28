package net.mcreator.ssc.integration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.ssc14.content.block.airlock.AirlockUpperPlugIntegrationService;
import net.ssc14.content.airlock.AirlockUpperPlugDestroyService;
import net.ssc14.content.airlock.AirlockUpperPlugValidationService;

/** Bridges canonical upper airlock plugs to historical procedures. */
public final class LegacyAirlockUpperPlugIntegrationAdapter
        implements AirlockUpperPlugIntegrationService.Adapter {
    @Override public void update(Level level, BlockPos pos) {
        AirlockUpperPlugValidationService.execute(
                level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override public void destroy(Level level, BlockPos pos) {
        AirlockUpperPlugDestroyService.execute(
                level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override public void use(Level level, BlockPos pos, Player player) {
        net.mcreator.ssc.procedures.AirlockUpPlug_ClickProcedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), player);
    }
}
