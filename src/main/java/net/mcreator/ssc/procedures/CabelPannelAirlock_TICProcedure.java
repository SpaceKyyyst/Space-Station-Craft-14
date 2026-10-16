package net.mcreator.ssc.procedures;

import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class CabelPannelAirlock_TICProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (!world.isClientSide() && entity instanceof Player player
                && player.containerMenu instanceof CabelPannelAirlockMenu menu && !menu.stillValid(player))
            player.closeContainer();
    }
}