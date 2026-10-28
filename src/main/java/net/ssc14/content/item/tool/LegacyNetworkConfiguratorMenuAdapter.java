package net.ssc14.content.item.tool;

import io.netty.buffer.Unpooled;
import net.ssc14.content.menu.network.NetConfigGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Compatibility NetConfig menu factory behind the canonical item service. */
public final class LegacyNetworkConfiguratorMenuAdapter
        implements NetworkConfiguratorService.Adapter {
    @Override
    public void openMenu(ServerPlayer player, BlockPos pos) {
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() { return Component.literal("NetConfigGUI"); }

            @Override
            public boolean shouldTriggerClientSideContainerClosingOnOpen() { return false; }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player menuPlayer) {
                return new NetConfigGUIMenu(id, inventory,
                        new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
            }
        }, pos);
    }
}
