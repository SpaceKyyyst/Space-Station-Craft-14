package net.ssc14.content.block.window;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.sound.Ssc14SoundEvents;

/** Preserves the legacy client-side empty-hand window knock interaction. */
public final class WindowKnockService {
    private WindowKnockService() {
    }

    public static InteractionResult knock(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return InteractionResult.PASS;
        }
        ItemStack held = entity instanceof LivingEntity living ? living.getMainHandItem() : ItemStack.EMPTY;
        if (ItemStack.EMPTY.getItem() != held.getItem()) {
            return InteractionResult.PASS;
        }
        if (world instanceof Level level && !level.isClientSide()) {
            return InteractionResult.PASS;
        }
        if (world instanceof Level level) {
            level.playLocalSound(x + 0.5, y + 0.5, z + 0.5, Ssc14SoundEvents.EFFECTS_GLASS_KNOCK.get(),
                    SoundSource.BLOCKS, random(0.5, 1.0), random(0.9, 1.1), false);
        }
        return InteractionResult.SUCCESS;
    }

    private static float random(double minimum, double maximum) {
        return (float) Mth.nextDouble(RandomSource.create(), minimum, maximum);
    }
}
