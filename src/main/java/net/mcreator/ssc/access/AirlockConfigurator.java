package net.mcreator.ssc.access;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.block.airlock.AirlockConfiguratorService;

/** Compatibility facade for the canonical access configurator service. */
public final class AirlockConfigurator {
    private AirlockConfigurator() {
    }

    public static long permissions(ItemStack configurator) {
        return AirlockConfiguratorService.permissions(configurator);
    }

    public static void synchronize(Player player, boolean initialize) {
        AirlockConfiguratorService.synchronize(player, initialize);
    }
}
