package net.ssc14.integration.neoforge.menu.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.ssc14.core.network.MenuAccessPolicy;

/** Resolves NeoForge/Minecraft state required by the pure menu access policy. */
public final class MenuAccessGuard {
    private MenuAccessGuard() { }

    public static boolean canUseMenu(Player player) {
        AbstractContainerMenu menu = player.containerMenu;
        return MenuAccessPolicy.canUse(player.isAlive(), player.isSpectator(),
                menu != null, menu != null && menu.stillValid(player));
    }

    public static boolean matches(Player player, AbstractContainerMenu menu,
            int x, int y, int z, int menuX, int menuY, int menuZ) {
        boolean coordinatesMatch = x == menuX && y == menuY && z == menuZ;
        boolean usable = canUseMenu(player);
        if (player.containerMenu != menu || !coordinatesMatch || !usable) return false;
        boolean chunkLoaded = player.level().getChunkSource().hasChunk(
                SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
        if (!chunkLoaded) return false;
        boolean mayInteract = !(player instanceof ServerPlayer)
                || player.level().mayInteract(player, new BlockPos(x, y, z));
        return MenuAccessPolicy.matches(true, true, true, true,
                player.distanceToSqr(x + 0.5, y + 0.5, z + 0.5), mayInteract);
    }
}
