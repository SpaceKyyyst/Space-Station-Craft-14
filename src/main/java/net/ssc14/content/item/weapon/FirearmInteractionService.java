package net.ssc14.content.item.weapon;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/** Boundary for firearm behavior that still depends on legacy procedures. */
public final class FirearmInteractionService {
    public interface Adapter {
        String tooltip(FirearmState state, ItemStack stack);

        void use(FirearmState state, Level level, Player player, ItemStack stack);

        void swing(Mk58State state, LivingEntity entity, ItemStack stack);

        void inventoryTick(
                FirearmState state,
                ItemStack stack,
                ServerLevel level,
                Entity entity,
                @Nullable EquipmentSlot equipmentSlot);
    }

    private static Adapter adapter = new Adapter() {
        @Override
        public String tooltip(FirearmState state, ItemStack stack) {
            return null;
        }

        @Override
        public void use(FirearmState state, Level level, Player player, ItemStack stack) { }

        @Override
        public void swing(Mk58State state, LivingEntity entity, ItemStack stack) { }

        @Override
        public void inventoryTick(
                FirearmState state,
                ItemStack stack,
                ServerLevel level,
                Entity entity,
                @Nullable EquipmentSlot equipmentSlot) { }
    };

    private FirearmInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static String tooltip(FirearmState state, ItemStack stack) {
        return adapter.tooltip(state, stack);
    }

    public static void use(
            FirearmState state, Level level, Player player, ItemStack stack) {
        adapter.use(state, level, player, stack);
    }

    public static void swing(Mk58State state, LivingEntity entity, ItemStack stack) {
        adapter.swing(state, entity, stack);
    }

    public static void inventoryTick(
            FirearmState state,
            ItemStack stack,
            ServerLevel level,
            Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        adapter.inventoryTick(state, stack, level, entity, equipmentSlot);
    }
}
