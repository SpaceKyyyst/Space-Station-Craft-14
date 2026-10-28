package net.ssc14.content.block.commerce;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.ssc14.content.commerce.VendingMachineRuntimeService;
import net.ssc14.content.menu.commerce.VendingAut1GUIMenu;

/** Canonical vending lifecycle and menu integration. */
public final class VendingMachineIntegrationService {
    private VendingMachineIntegrationService() { }

    public static void onPlaced(Level level, BlockPos pos) {
        VendingMachineRuntimeService.initialize(level, pos);
    }

    public static void onNeighborChanged(Level level, BlockPos pos) {
        VendingMachineRuntimeService.validateUpperPlug(level, pos);
    }

    public static void onAttack(Level level, BlockPos pos, Player player) {
        VendingMachineRuntimeService.handleCreativeAttack(level, pos, player);
    }

    public static void open(Level level, BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer
                && level.getBlockEntity(pos) instanceof MenuProvider provider) {
            serverPlayer.openMenu(provider, pos);
        }
    }

    public static AbstractContainerMenu createMenu(
            int id, Inventory inventory, BlockPos pos) {
        return new VendingAut1GUIMenu(id, inventory,
                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
    }
}
