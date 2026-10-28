package net.mcreator.ssc.integration;

import io.netty.buffer.Unpooled;
import net.mcreator.ssc.procedures.VendingAut1_CREATIVE_DESTROY_Procedure;
import net.mcreator.ssc.procedures.VendingAut_GENERATE_Procedure;
import net.mcreator.ssc.procedures.VendingAut_UPDATE_Procedure;
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

/** Connects the canonical vending machine to remaining legacy procedures and menu. */
public final class LegacyVendingMachineIntegrationAdapter
        implements VendingMachineIntegrationService.Adapter {
    @Override public void onPlaced(Level level, BlockPos pos) {
        VendingAut_GENERATE_Procedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
    }
    @Override public void onNeighborChanged(Level level, BlockPos pos) {
        VendingAut_UPDATE_Procedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
    }
    @Override public void onAttack(Level level, BlockPos pos, Player player) {
        VendingAut1_CREATIVE_DESTROY_Procedure.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), player);
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
