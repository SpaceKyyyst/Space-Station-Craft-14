package net.ssc14.content.item.food;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.Ssc14Items;

/** Canonical compatibility operation for unwrapping the SSC14 energy bar. */
public final class EnergyBarOpeningService {
    public static final int TRASH_PICKUP_DELAY_TICKS = 1;

    private EnergyBarOpeningService() { }

    public static void open(LevelAccessor level, double x, double y, double z,
            Entity entity, ItemStack wrappedBar) {
        if (entity == null) {
            return;
        }

        wrappedBar.shrink(1);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND,
                    new ItemStack(Ssc14Items.ENERGY_BAR_2.get()));
            if (livingEntity instanceof Player player) {
                player.getInventory().setChanged();
            }
        }

        if (level instanceof ServerLevel serverLevel) {
            ItemEntity trash = new ItemEntity(serverLevel, x, y, z,
                    new ItemStack(Ssc14Items.ENERGY_BAR_TRASH.get()));
            trash.setPickUpDelay(TRASH_PICKUP_DELAY_TICKS);
            trash.setUnlimitedLifetime();
            serverLevel.addFreshEntity(trash);
        }
    }
}
