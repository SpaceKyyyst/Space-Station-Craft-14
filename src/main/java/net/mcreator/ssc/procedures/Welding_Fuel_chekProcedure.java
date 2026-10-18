package net.mcreator.ssc.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.mcreator.ssc.item.WelderFuelService;

/** Compatibility entry point for old generated callers; fuel logic lives in WelderFuelService. */
public final class Welding_Fuel_chekProcedure {
    private Welding_Fuel_chekProcedure() {
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack stack) {
        if (!(world instanceof ServerLevel level) || !(entity instanceof ServerPlayer player)) return;
        InteractionHand hand = player.getMainHandItem() == stack ? InteractionHand.MAIN_HAND
                : player.getOffhandItem() == stack ? InteractionHand.OFF_HAND : null;
        if (hand != null) WelderFuelService.tick(level, player, hand, stack);
    }
}
