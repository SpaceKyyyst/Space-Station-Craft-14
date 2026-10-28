package net.ssc14.content.item.container;

import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

/** Boundary for portable menus that still use legacy menu implementations. */
public final class PortableMenuInteractionService {
    @FunctionalInterface
    public interface Adapter {
        void open(PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand);

        default boolean canOpen(
                PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
            return true;
        }

        default void inventoryTick(
                PortableMenuItemKind kind,
                ItemStack stack,
                ServerLevel level,
                Entity entity,
                @Nullable EquipmentSlot equipmentSlot) { }
    }

    private static Adapter adapter = (kind, player, hand) -> { };

    private PortableMenuInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void open(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        adapter.open(kind, player, hand);
    }

    public static boolean canOpen(
            PortableMenuItemKind kind, ServerPlayer player, InteractionHand hand) {
        return adapter.canOpen(kind, player, hand);
    }

    public static void inventoryTick(
            PortableMenuItemKind kind,
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        adapter.inventoryTick(kind, stack, level, entity, equipmentSlot);
    }
}
