package net.mcreator.ssc.integration;

import io.netty.buffer.Unpooled;
import net.ssc14.content.menu.access.IDcodeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.ssc14.content.block.access.IdConsoleIntegrationService;

/** Connects the canonical ID console to its legacy menu and sound procedure. */
public final class LegacyIdConsoleIntegrationAdapter
        implements IdConsoleIntegrationService.Adapter {
    @Override public void use(Level level, BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override public Component getDisplayName() {
                    return Component.literal("Console Of ID-card");
                }
                @Override public AbstractContainerMenu createMenu(
                        int id, Inventory inventory, Player menuPlayer) {
                    return LegacyIdConsoleIntegrationAdapter.this.createMenu(
                            id, inventory, pos);
                }
            }, pos);
        }
        net.ssc14.content.sound.ConsoleMenuSoundService.play(
                level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override public AbstractContainerMenu createMenu(
            int id, Inventory inventory, BlockPos pos) {
        return new IDcodeMenu(id, inventory,
                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
    }
}
