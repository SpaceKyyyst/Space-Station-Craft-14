package net.ssc14.content.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.ssc14.content.menu.construction.RCDGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIwallfloorMenu;
import net.ssc14.content.menu.construction.RCDGUIwindowsMenu;
import io.netty.buffer.Unpooled;

/** Applies RCD mode selections from the GUI. */
public final class RcdModeService {
    private RcdModeService() { }

    public static void armWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 10));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void wall(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 2));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void directionalArmWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 9));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void deconstruct(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 1));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void catwalk(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 5));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void window(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 7));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void directionalWindow(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 8));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void grille(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 6));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void heating(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 4));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void tile(Entity entity) {
        if (entity == null) return;
        ItemStack stack = entity instanceof Player player ? player.getMainHandItem() : ItemStack.EMPTY;
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Mode", 3));
        if (entity instanceof Player player) player.closeContainer();
    }

    public static void openMain(Player player, BlockPos pos) {
        if (player instanceof ServerPlayer server) open(server, pos, "RCDGUI", 0);
    }

    public static void openWallfloor(Player player, BlockPos pos) {
        if (player instanceof ServerPlayer server) open(server, pos, "RCDGUIwallfloor", 1);
    }

    public static void openWindows(Player player, BlockPos pos) {
        if (player instanceof ServerPlayer server) open(server, pos, "RCDGUIwindows", 2);
    }

    private static void open(ServerPlayer player, BlockPos pos, String title, int menu) {
        player.openMenu(new MenuProvider() {
            @Override public Component getDisplayName() { return Component.literal(title); }
            @Override public boolean shouldTriggerClientSideContainerClosingOnOpen() { return false; }
            @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, Player ignored) {
                FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos);
                return menu == 0 ? new RCDGUIMenu(id, inventory, buffer)
                        : menu == 1 ? new RCDGUIwallfloorMenu(id, inventory, buffer)
                        : new RCDGUIwindowsMenu(id, inventory, buffer);
            }
        }, pos);
    }
}
