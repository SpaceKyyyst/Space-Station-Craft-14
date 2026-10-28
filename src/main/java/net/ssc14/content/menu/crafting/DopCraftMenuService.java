package net.ssc14.content.menu.crafting;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Opens or closes the additional-crafting menu for a player. */
public final class DopCraftMenuService {
    private static final Component TITLE = Component.literal("DopCraftMenu");

    private DopCraftMenuService() { }

    public static void toggle(Player player) {
        if (player == null) return;
        if (player.containerMenu instanceof DopCraftMenu) {
            player.closeContainer();
            return;
        }
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        BlockPos source = player.blockPosition();
        serverPlayer.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return TITLE;
            }

            @Override
            public boolean shouldTriggerClientSideContainerClosingOnOpen() {
                return false;
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player menuPlayer) {
                return new DopCraftMenu(id, inventory,
                        new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(source));
            }
        }, source);
    }
}
