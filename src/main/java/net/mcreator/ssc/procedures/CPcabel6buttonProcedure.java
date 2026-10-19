package net.mcreator.ssc.procedures;

import net.mcreator.ssc.access.AirlockWireService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

/** Compatibility adapter for wire button 6. */
public class CPcabel6buttonProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity instanceof Player player && player.level() == world)
            AirlockWireService.toggle(player, BlockPos.containing(x, y, z), 6);
    }
}