package net.mcreator.ssc.integration;

import io.netty.buffer.Unpooled;
import net.ssc14.content.commerce.VendingMachineRuntimeService;
import net.ssc14.content.menu.commerce.VendingAut1GUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.ssc14.content.block.commerce.VendingMachineIntegrationService;

/** Retains only the legacy bootstrap boundary for the canonical vending menu. */
public final class LegacyVendingMachineIntegrationAdapter
        implements VendingMachineIntegrationService.Adapter {
    @Override public void onPlaced(Level level, BlockPos pos) {
        VendingMachineRuntimeService.initialize(level, pos);
    }
    @Override public void onNeighborChanged(Level level, BlockPos pos) {
        VendingMachineRuntimeService.validateUpperPlug(level, pos);
    }
    @Override public void onAttack(Level level, BlockPos pos, Player player) {
        VendingMachineRuntimeService.handleCreativeAttack(level, pos, player);
    }
    @Override public void open(Level level, BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override public Component getDisplayName() {
                    return Component.literal("Vending Automate (Snaks blue)");
                }
                @Override public AbstractContainerMenu createMenu(
                        int id, Inventory inventory, Player menuPlayer) {
                    return LegacyVendingMachineIntegrationAdapter.this.createMenu(
                            id, inventory, pos);
                }
            }, pos);
        }
    }
    @Override public AbstractContainerMenu createMenu(
            int id, Inventory inventory, BlockPos pos) {
        return new VendingAut1GUIMenu(id, inventory,
                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
    }
}
