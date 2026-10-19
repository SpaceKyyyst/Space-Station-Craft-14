package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.mcreator.ssc.access.AirlockConfigurator;

/** Compatibility entry point; authorization and target binding belong to the configurator service. */
public final class AcsConfigMenu0CellUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity instanceof Player player) AirlockConfigurator.synchronize(player, true);
    }
}