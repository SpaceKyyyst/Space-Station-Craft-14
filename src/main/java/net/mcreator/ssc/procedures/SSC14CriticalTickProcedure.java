
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.AABB;
import net.minecraft.resources.ResourceLocation;

@EventBusSubscriber
public class SSC14CriticalTickProcedure {

    private static final String KEY_TOTAL = "sscCustomHealth";
    private static final String KEY_CRITICAL = "ssc14_critical";
    private static final String KEY_ASPHYX_TIMER = "ssc14_asphyxTimer";
    private static final String PREFIX_TYPE = "ssc14_dmg_";
    
    private static final double CRIT_MIN = 100.0;
    private static final double CRIT_MAX = 200.0;
    private static final int ASPHYX_INTERVAL_TICKS = 20;
    
    private static final double CRIT_WIDTH = 0.6;
    private static final double CRIT_HEIGHT = 0.45;
    
    private static final ResourceLocation CRIT_IMMOBILIZE_ID = ResourceLocation.parse("ssc14:crit_immobilize");
    private static final ResourceLocation CRIT_NOJUMP_ID = ResourceLocation.parse("ssc14:crit_nojump");

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        var nbt = player.getPersistentData();
        
        // 🔧 Читаем урон и вычисляем крит (работает на обеих сторонах)
        double totalDamage = nbt.getDouble(KEY_TOTAL).orElse(0.0);
        boolean isCritical = (totalDamage >= CRIT_MIN && totalDamage < CRIT_MAX);
        
        // === СЕРВЕРНАЯ ЛОГИКА: урон, атрибуты, mayBuild ===
        if (!player.level().isClientSide()) {
            if (isCritical) {
                // 💨 Удушье
                int timer = nbt.getInt(KEY_ASPHYX_TIMER).orElse(0);
                if (timer >= ASPHYX_INTERVAL_TICKS) {
                    double asphyx = nbt.getDouble(PREFIX_TYPE + "asphyx").orElse(0.0) + 1.0;
                    nbt.putDouble(PREFIX_TYPE + "asphyx", asphyx);
                    nbt.putDouble(KEY_TOTAL, totalDamage + 1.0);
                    nbt.putInt(KEY_ASPHYX_TIMER, 0);
                    System.out.println("[SSC14] 💨 Удушье: +1 | Всего: " + (totalDamage + 1.0));
                } else {
                    nbt.putInt(KEY_ASPHYX_TIMER, timer + 1);
                }
                
                // 🛑 Иммобилизация (атрибуты)
                applyCriticalModifiers(player);
                
                // 🔧 Блокировка строительства
                player.getAbilities().mayBuild = false;
                player.onUpdateAbilities();
            } else {
                // 🔹 Восстановление mayBuild при выходе из крита
                if (!player.getAbilities().mayBuild) {
                    player.getAbilities().mayBuild = true;
                    player.onUpdateAbilities();
                }
                removeCriticalModifiers(player);
            }
        }
        
        // === ВИЗУАЛ + ХИТБОКС: ПРИМЕНЯЕМ НА ОБЕИХ СТОРОНАХ ===
        if (isCritical) {
            // 🛌 Поза: каждый тик, клиент + сервер
            if (player.getPose() != Pose.SWIMMING) {
                player.setPose(Pose.SWIMMING);
                player.refreshDimensions(); // 🔹 Синхронизирует хитбокс с клиентом
            }
            
            // 📦 Хитбокс: каждый тик, клиент + сервер (защита от сброса)
            double x = player.getX(), y = player.getY(), z = player.getZ();
            player.setBoundingBox(new AABB(
                x - CRIT_WIDTH / 2, y, z - CRIT_WIDTH / 2,
                x + CRIT_WIDTH / 2, y + CRIT_HEIGHT, z + CRIT_WIDTH / 2
            ));
            
            // 👁 Слепота: добавляем на сервере, но эффект виден на клиенте
            if (!player.level().isClientSide() && !player.hasEffect(MobEffects.BLINDNESS)) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 220, 0, false, false));
            }
        } else {
            // 🔹 Восстановление позы и хитбокса при выходе из крита
            if (player.getPose() == Pose.SWIMMING) {
                player.setPose(Pose.STANDING);
                player.refreshDimensions();
            }
            if (!player.level().isClientSide()) {
                player.removeEffect(MobEffects.BLINDNESS);
            }
        }
    }
    
    private static void applyCriticalModifiers(Player player) {
        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && !speedAttr.hasModifier(CRIT_IMMOBILIZE_ID)) {
            speedAttr.addTransientModifier(new AttributeModifier(CRIT_IMMOBILIZE_ID, -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        var jumpAttr = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpAttr != null && !jumpAttr.hasModifier(CRIT_NOJUMP_ID)) {
            jumpAttr.addTransientModifier(new AttributeModifier(CRIT_NOJUMP_ID, -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }
    
    private static void removeCriticalModifiers(Player player) {
        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && speedAttr.hasModifier(CRIT_IMMOBILIZE_ID)) speedAttr.removeModifier(CRIT_IMMOBILIZE_ID);
        var jumpAttr = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpAttr != null && jumpAttr.hasModifier(CRIT_NOJUMP_ID)) jumpAttr.removeModifier(CRIT_NOJUMP_ID);
    }
    
    private static void updateHealthUIAttribute(Player player, double totalDamage) {
        var attr = player.getAttribute(net.mcreator.ssc.init.Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
        if (attr == null) return;
        int uiState = (totalDamage <= 12) ? 0 : (totalDamage <= 37) ? 1 : (totalDamage <= 62) ? 2 : 
                      (totalDamage <= 87) ? 3 : (totalDamage <= 100) ? 4 : (totalDamage <= 200) ? 5 : 6;
        attr.setBaseValue(uiState);
    }
}
