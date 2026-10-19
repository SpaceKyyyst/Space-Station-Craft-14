package net.mcreator.ssc.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.world.inventory.DECALScolorGUIMenu;
import net.mcreator.ssc.world.inventory.IDcodeMenu;

/** Authorization at the boundary between UI payloads and gameplay procedures. */
public final class MenuPacketGuard {
    private static final double MAX_MENU_DISTANCE_SQUARED = 64.0;

    private MenuPacketGuard() {
    }

    public static boolean canUseMenu(Player player) {
        return player.isAlive() && !player.isSpectator() && player.containerMenu != null
                && player.containerMenu.stillValid(player);
    }

    public static boolean matches(Player player, AbstractContainerMenu menu,
            int x, int y, int z, int menuX, int menuY, int menuZ) {
        if (player.containerMenu != menu || x != menuX || y != menuY || z != menuZ || !canUseMenu(player)) {
            return false;
        }
        if (!player.level().getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))) {
            return false;
        }
        return player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5) <= MAX_MENU_DISTANCE_SQUARED
                && (!(player instanceof ServerPlayer) || player.level().mayInteract(player, new BlockPos(x, y, z)));
    }

    public static boolean canEditMenuState(Player player) {
        return switch (player.containerMenu) {
            case AccessConfigMENUMenu menu -> matches(player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case IDcodeMenu menu -> matches(player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case DECALScolorGUIMenu menu -> matches(player, menu, menu.x, menu.y, menu.z, menu.x, menu.y, menu.z);
            case null, default -> false;
        };
    }
}
