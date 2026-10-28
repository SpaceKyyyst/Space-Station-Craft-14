package net.ssc14.content.item.weapon;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
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

    public static void initializeMk58(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!data.copyTag().getBooleanOr("GenEraTE", false)) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putBoolean("GenEraTE", true);
                tag.putDouble("MagazBullets", 10);
                tag.putDouble("BulletChamber", 1);
            });
        }
    }

    public static void initializePistolMagazine(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!data.copyTag().getBooleanOr("GenEraTE", false)) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putBoolean("GenEraTE", true);
                tag.putDouble("Bullets", 10);
            });
        }
    }

    /** Handles MK-58 breech and magazine transitions without generated procedures. */
    public static void toggleMk58(Level level, LivingEntity entity, ItemStack stack,
            Item mk58, Item open, Item noMagazine, Item openNoMagazine,
            Item magazine, Item emptyMagazine, Item bullet) {
        if (level.isClientSide() || entity.getMainHandItem() != stack) return;
        boolean shift = entity.isShiftKeyDown();
        Item current = stack.getItem();
        double chamber = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("BulletChamber", 0);
        double bullets = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("MagazBullets", 0);
        if (shift && (current == mk58 || current == open || current == noMagazine
                || current == openNoMagazine)) {
            ItemStack offhand = entity.getOffhandItem();
            boolean hasMagazine = offhand.is(magazine) || offhand.is(emptyMagazine);
            if (current == mk58 || current == open) {
                ItemStack removed = new ItemStack(bullets > 0 ? magazine : emptyMagazine);
                updateBullets(removed, bullets);
                entity.setItemInHand(InteractionHand.OFF_HAND, removed);
                ItemStack replacement = new ItemStack(current == mk58 ? noMagazine : openNoMagazine);
                copyMk58Data(stack, replacement, chamber, bullets);
                entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
            } else if (hasMagazine) {
                double loaded = readBullets(offhand);
                ItemStack replacement = new ItemStack(current == noMagazine ? mk58 : open);
                copyMk58Data(stack, replacement, chamber, loaded);
                entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
                entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            return;
        }
        if (current == mk58) {
            ItemStack replacement = new ItemStack(open);
            copyMk58Data(stack, replacement, 0, bullets);
            entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
            ejectBullet(level, entity, bullet, chamber);
        } else if (current == open) {
            ItemStack replacement = new ItemStack(bullets > 0 ? mk58 : mk58);
            copyMk58Data(stack, replacement, bullets > 0 ? 1 : 0,
                    Math.max(0, bullets - (bullets > 0 ? 1 : 0)));
            entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
        } else if (current == noMagazine) {
            ItemStack replacement = new ItemStack(openNoMagazine);
            copyMk58Data(stack, replacement, 0, 0);
            entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
            ejectBullet(level, entity, bullet, chamber);
        } else if (current == openNoMagazine) {
            ItemStack replacement = new ItemStack(noMagazine);
            copyMk58Data(stack, replacement, chamber, 0);
            entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
        }
    }

    private static void copyMk58Data(ItemStack source, ItemStack target,
            double chamber, double bullets) {
        CustomData.update(DataComponents.CUSTOM_DATA, target, tag -> {
            tag.putBoolean("GenEraTE", true);
            tag.putDouble("BulletChamber", chamber);
            tag.putDouble("MagazBullets", bullets);
        });
    }

    private static void ejectBullet(Level level, LivingEntity entity, Item bullet, double chamber) {
        if (chamber > 0 && level instanceof ServerLevel server) {
            server.addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(server,
                    entity.getX(), entity.getY() + entity.getBbHeight() / 3.0D,
                    entity.getZ(), new ItemStack(bullet)));
        }
    }

    public static void usePistolMagazine(Level level, Entity entity, ItemStack magazine,
            Item loadedItem, Item emptyItem, Item bulletItem) {
        if (!(entity instanceof LivingEntity living) ||
                (living.getMainHandItem().getItem() != loadedItem && living.getMainHandItem().getItem() != emptyItem)) {
            return;
        }
        ItemStack offhand = living.getOffhandItem();
        double bullets = magazine.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("Bullets", 0);
        if (offhand.is(bulletItem) && bullets < 10) {
            offhand.shrink(1);
            updateBullets(magazine, bullets + 1);
        } else if (offhand.isEmpty() && bullets > 0) {
            updateBullets(magazine, bullets - 1);
            living.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, new ItemStack(bulletItem));
        } else if (bullets > 0 && level instanceof ServerLevel server) {
            updateBullets(magazine, bullets - 1);
            server.addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(server, entity.getX(),
                    entity.getY() + entity.getBbHeight() / 3.0D, entity.getZ(), new ItemStack(bulletItem)));
        }
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(magazine, 5);
        }
        if (magazine.getItem() == loadedItem && readBullets(magazine) == 0) {
            living.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new ItemStack(emptyItem));
        } else if (magazine.getItem() == emptyItem && readBullets(magazine) != 0) {
            magazine.shrink(1);
            ItemStack loaded = new ItemStack(loadedItem);
            updateBullets(loaded, 1);
            living.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, loaded);
        }
    }

    private static double readBullets(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("Bullets", 0);
    }

    private static void updateBullets(ItemStack stack, double value) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("Bullets", value));
    }

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
