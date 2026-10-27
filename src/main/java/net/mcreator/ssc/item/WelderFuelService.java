package net.mcreator.ssc.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.WelderStateService;

/** @deprecated Use {@link WelderStateService}. */
@Deprecated(forRemoval = false)
public final class WelderFuelService {
    private WelderFuelService() { }

    public static void tick(ServerLevel level, Player player, InteractionHand hand,
            ItemStack stack) {
        WelderStateService.tick(level, player, hand, stack);
    }
}
