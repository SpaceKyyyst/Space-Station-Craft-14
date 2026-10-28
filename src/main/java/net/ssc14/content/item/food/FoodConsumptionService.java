package net.ssc14.content.item.food;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Server-authoritative timed bite state machine shared by all SSC14 food items. */
public final class FoodConsumptionService {
    private static final String EATING_PROGRESS = "EatingProgress";
    private static final String BUTTON_TRIGGERED = "ButtonTriggered";
    private static final String NUTRIENTS = "Nutrients";
    private static final int COMPLETE_PROGRESS = 6;
    private static final int STEP_DELAY_TICKS = 5;
    private static final int TRASH_PICKUP_DELAY_TICKS = 1;
    private static final double FULLNESS_LIMIT = 10.0;
    private static final Identifier EATING_SOUND = Identifier.fromNamespaceAndPath(
            Ssc14Identity.MOD_ID, "eating_sounds");

    private FoodConsumptionService() { }

    public static void consume(LevelAccessor world, double x, double y, double z,
            Entity entity, ItemStack itemStack, FoodDefinition food) {
        if (!(world instanceof ServerLevel serverLevel)
                || !(entity instanceof LivingEntity livingEntity)
                || itemStack == null || itemStack.isEmpty()) {
            return;
        }
        CompoundTag data = itemStack.getOrDefault(
                DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int progress = data.getInt(EATING_PROGRESS).orElse(0);

        if (progress == 0 && digestion(livingEntity) >= FULLNESS_LIMIT) {
            if (entity instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("В вас больше не влезет!"), true);
            }
            resetProgress(livingEntity);
            return;
        }

        if (progress > 0) {
            if (data.getBoolean(BUTTON_TRIGGERED).orElse(false)) {
                resetItemProgress(itemStack);
                resetProgress(livingEntity);
                return;
            }
        } else {
            CustomData.update(DataComponents.CUSTOM_DATA, itemStack,
                    tag -> tag.putBoolean(BUTTON_TRIGGERED, true));
        }

        ItemStack handItem = livingEntity.getMainHandItem();
        if (handItem.isEmpty() || handItem.getItem() != itemStack.getItem()) {
            resetProgress(livingEntity);
            return;
        }

        if (progress < COMPLETE_PROGRESS) {
            advance(serverLevel, x, y, z, livingEntity, handItem, food, progress + 1);
            return;
        }
        finishBite(serverLevel, x, y, z, livingEntity, handItem, food,
                digestion(livingEntity));
    }

    private static void advance(ServerLevel level, double x, double y, double z,
            LivingEntity livingEntity, ItemStack handItem, FoodDefinition food, int progress) {
        CustomData.update(DataComponents.CUSTOM_DATA, handItem, tag -> {
            tag.putInt(EATING_PROGRESS, progress);
            tag.putBoolean(BUTTON_TRIGGERED, false);
        });
        if (livingEntity.getAttributes().hasAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB)) {
            livingEntity.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).setBaseValue(progress);
        }
        ServerWorkScheduler.schedule(level, STEP_DELAY_TICKS,
                () -> consume(level, x, y, z, livingEntity, handItem, food));
    }

    private static void finishBite(ServerLevel level, double x, double y, double z,
            LivingEntity livingEntity, ItemStack itemStack, FoodDefinition food,
            double digestion) {
        resetItemProgress(itemStack);
        level.playSound(null, BlockPos.containing(x, y, z),
                BuiltInRegistries.SOUND_EVENT.getValue(EATING_SOUND), SoundSource.NEUTRAL,
                randomPitch(), randomPitch());
        if (livingEntity.getAttributes().hasAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES)) {
            livingEntity.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES)
                    .setBaseValue(digestion + 1.0);
        }
        if (livingEntity instanceof ServerPlayer player) {
            player.sendSystemMessage(Component.literal(food.tasteMessage()), true);
        }
        DigestionService.schedule(level, livingEntity);

        CompoundTag data = itemStack.getOrDefault(
                DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        double completedBites = data.getDoubleOr(NUTRIENTS, 0.0) + 1.0;
        if (completedBites < food.maxBites()) {
            CustomData.update(DataComponents.CUSTOM_DATA, itemStack,
                    tag -> tag.putDouble(NUTRIENTS, completedBites));
            consume(level, x, y, z, livingEntity, itemStack, food);
            return;
        }
        if (food.trash() != null) {
            ItemEntity trash = new ItemEntity(level, x, y, z, new ItemStack(food.trash().get()));
            trash.setPickUpDelay(TRASH_PICKUP_DELAY_TICKS);
            trash.setUnlimitedLifetime();
            level.addFreshEntity(trash);
        }
        itemStack.shrink(1);
        CustomData.update(DataComponents.CUSTOM_DATA, itemStack,
                tag -> tag.putDouble(NUTRIENTS, 0.0));
        resetProgress(livingEntity);
    }

    private static double digestion(LivingEntity entity) {
        return entity.getAttributes().hasAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES)
                ? entity.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES).getValue() : 0.0;
    }

    private static float randomPitch() {
        return (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1);
    }

    private static void resetItemProgress(ItemStack itemStack) {
        CustomData.update(DataComponents.CUSTOM_DATA, itemStack, tag -> {
            tag.putInt(EATING_PROGRESS, 0);
            tag.putBoolean(BUTTON_TRIGGERED, false);
        });
    }

    private static void resetProgress(LivingEntity livingEntity) {
        if (livingEntity.getAttributes().hasAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB)) {
            livingEntity.getAttribute(Ssc14Attributes.PROGRESS_BAR_ATRB).setBaseValue(0.0);
        }
    }
}
