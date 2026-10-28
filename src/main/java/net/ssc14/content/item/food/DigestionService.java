package net.ssc14.content.item.food;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.attribute.Ssc14Attributes;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

/** Owns delayed nutrient absorption after a completed bite. */
public final class DigestionService {
    private static final double NUTRIENTS_PER_CYCLE = 5.0;
    private static final int BASE_DIGESTION_TICKS = 400;
    private static final double MAX_NUTRIENTS = 200.0;
    private static final double ROUNDING_SCALE = 100_000.0;
    private static final double MOD_OVEREATING = 0.833;
    private static final double MOD_SATIATED = 1.0;
    private static final double MOD_HUNGER = 1.25;
    private static final double MOD_STARVATION = 1.667;

    private DigestionService() { }

    public static void schedule(LevelAccessor world, Entity entity) {
        if (!(world instanceof ServerLevel serverLevel)
                || !(entity instanceof LivingEntity livingEntity)
                || !isMetabolismActive(livingEntity)) {
            return;
        }
        Double currentNutrients = nutrients(livingEntity);
        if (currentNutrients == null) {
            return;
        }
        int delayTicks = (int) Math.round(BASE_DIGESTION_TICKS
                * digestionModifier(currentNutrients));
        ServerWorkScheduler.schedule(serverLevel, delayTicks, () -> {
            if (!livingEntity.isRemoved() && !livingEntity.level().isClientSide()) {
                processDigestion(livingEntity);
            }
        });
    }

    private static void processDigestion(LivingEntity entity) {
        Double nutrientsBefore = nutrients(entity);
        if (nutrientsBefore != null) {
            setNutrients(entity, nutrientsBefore + NUTRIENTS_PER_CYCLE);
        }
        AttributeInstance digestive = entity.getAttribute(Ssc14Attributes.DIGESTIVE_PROCESSES);
        if (digestive != null) {
            digestive.setBaseValue(Math.round(Math.max(0.0, digestive.getValue() - 1.0)));
        }
    }

    private static boolean isMetabolismActive(LivingEntity entity) {
        if (!entity.getAttributes().hasAttribute(Ssc14Attributes.NUTRIENTS)) {
            return false;
        }
        if (entity instanceof Player player) {
            GameType gameType = player.gameMode();
            return gameType != GameType.CREATIVE && gameType != GameType.SPECTATOR;
        }
        return true;
    }

    private static Double nutrients(LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Ssc14Attributes.NUTRIENTS);
        return attribute == null ? null : attribute.getValue();
    }

    private static void setNutrients(LivingEntity entity, double value) {
        AttributeInstance attribute = entity.getAttribute(Ssc14Attributes.NUTRIENTS);
        if (attribute != null) {
            double clamped = Math.max(0.0, Math.min(MAX_NUTRIENTS, value));
            attribute.setBaseValue(Math.round(clamped * ROUNDING_SCALE) / ROUNDING_SCALE);
        }
    }

    private static double digestionModifier(double nutrients) {
        if (nutrients >= 151.0) return MOD_OVEREATING;
        if (nutrients >= 101.0) return MOD_SATIATED;
        if (nutrients >= 51.0) return MOD_HUNGER;
        return MOD_STARVATION;
    }
}
