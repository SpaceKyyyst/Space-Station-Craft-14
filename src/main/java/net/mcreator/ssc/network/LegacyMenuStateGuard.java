package net.mcreator.ssc.network;

import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.ssc14.content.menu.decal.DECALScolorGUIMenu;
import net.mcreator.ssc.world.inventory.IDcodeMenu;
import net.minecraft.world.entity.player.Player;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;

/** Transitional type adapter for the three generated menus with writable state. */
public final class LegacyMenuStateGuard {
    private LegacyMenuStateGuard() { }

    public static boolean canEdit(Player player) {
        return switch (player.containerMenu) {
            case AccessConfigMENUMenu menu -> MenuAccessGuard.matches(
                    player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case IDcodeMenu menu -> MenuAccessGuard.matches(
                    player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case DECALScolorGUIMenu menu -> MenuAccessGuard.matches(
                    player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case null, default -> false;
        };
    }
}
