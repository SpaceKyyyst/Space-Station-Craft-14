package net.ssc14.content.item.tool;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import io.netty.buffer.Unpooled;
import net.ssc14.content.menu.construction.RCDGUIMenu;

/** Boundary for the RSU runtime retained during incremental migration. */
public final class RsuInteractionService {
    private static Adapter adapter = Adapter.NO_OP;

    private RsuInteractionService() { }

    public static void useLegacy(Level level, Player player, ItemStack stack, ItemStack cartridge) {
        if (!player.isShiftKeyDown()) {
            return;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            BlockPos pos = player.blockPosition();
            serverPlayer.openMenu(new MenuProvider() {
                @Override public Component getDisplayName() { return Component.literal("RCDGUI"); }
                @Override public boolean shouldTriggerClientSideContainerClosingOnOpen() { return false; }
                @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, Player ignored) {
                    return new RCDGUIMenu(id, inventory,
                            new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
        if (stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                net.minecraft.world.item.component.CustomData.EMPTY).copyTag().getDoubleOr("Charges", 0) >= 60
                || player.getOffhandItem().getItem() != cartridge
                || player.getMainHandItem().getItem() != stack.getItem()) {
            return;
        }
        while (stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                net.minecraft.world.item.component.CustomData.EMPTY).copyTag().getDoubleOr("Charges", 0) < 60
                && player.getOffhandItem().getItem() == cartridge
                && player.getMainHandItem().getItem() == stack.getItem()) {
            net.minecraft.world.item.component.CustomData.update(
                    net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack,
                    tag -> tag.putDouble("Charges", tag.getDoubleOr("Charges", 0) + 1));
            player.getOffhandItem().shrink(1);
        }
    }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(Level level, Player player, InteractionHand hand,
            ItemStack stack) {
        adapter.use(level, player, hand, stack);
    }

    public static void useOn(UseOnContext context) {
        adapter.useOn(context);
    }

    public static void inventoryTick(ServerLevel level, Entity entity,
            ItemStack stack) {
        adapter.inventoryTick(level, entity, stack);
    }

    public interface Adapter {
        Adapter NO_OP = new Adapter() { };

        default void use(Level level, Player player, InteractionHand hand,
                ItemStack stack) { }

        default void useOn(UseOnContext context) { }

        default void inventoryTick(ServerLevel level, Entity entity,
                ItemStack stack) { }
    }
}
