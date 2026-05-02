
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber  // ← БЕЗ modid! MCreator сам разберётся
public class OnPlayerTakeDamageSSC14Procedure {

    @SubscribeEvent
    public static void onEntityAttacked(LivingDamageEvent.Pre event) {
        // Вызываем execute с параметрами, как в шаблоне MCreator
        execute(event, event.getEntity().level(), event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        // 🔍 Отладка: проверяем, что метод вызывается
        System.out.println("[SSC14-DEBUG] execute() вызван! event=" + (event != null ? event.getClass().getSimpleName() : "null"));
        
        // === ПРЕАМБУЛА: валидация ===
        if (!(event instanceof LivingDamageEvent.Pre damageEvent)) {
            System.out.println("[SSC14-DEBUG] Не LivingDamageEvent.Pre, выход");
            return;
        }
        if (!(entity instanceof Player player)) {
            System.out.println("[SSC14-DEBUG] Не Player, выход");
            return;
        }
        if (world.isClientSide()) {
            System.out.println("[SSC14-DEBUG] ClientSide, выход");
            return;
        }

        // 🔍 Лог: урон перехвачен
        System.out.println("[SSC14-DEBUG] ✅ Урон перехвачен! Исходящий: " + damageEvent.getOriginalDamage());

        // Отменяем ванильный урон
        damageEvent.setNewDamage(0);
        
        var nbt = player.getPersistentData();
        float incoming = damageEvent.getOriginalDamage();
        DamageSource source = damageEvent.getSource();

        // === ШАГ 1: Определяем тип урона ===
        String damageType = mapDamageType(source, player);
        
        // === ШАГ 2: Накатываем урон ===
        String typeKey = "ssc14_dmg_" + damageType;
        double typeDamage = nbt.getDouble(typeKey).orElse(0.0) + incoming;
        nbt.putDouble(typeKey, typeDamage);
        
        double totalDamage = nbt.getDouble("ssc14_damage").orElse(0.0) + incoming;
        nbt.putDouble("ssc14_damage", totalDamage);
        
        System.out.println("[SSC14] +" + incoming + " " + damageType + 
                          " | Всего: " + totalDamage + " | " + typeKey + ": " + typeDamage);

        // === ШАГ 3: Замедление по порогам ===
        applySlowdown(player, totalDamage);
        
        // === ШАГ 4: Проверка на гиббинг ===
        checkGibbing(player, nbt);
        
        // === ШАГ 5: Флаг кровотечения ===
        if (damageType.equals("slash") && typeDamage > 10) {
            if (!nbt.getBoolean("ssc14_bleeding").orElse(false)) {
                nbt.putBoolean("ssc14_bleeding", true);
                System.out.println("[SSC14] 🩸 Кровотечение: " + player.getName().getString());
            }
        }
    }
    
    // === ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (те же) ===
    
    private static String mapDamageType(DamageSource source, Player player) {
        String msgId = source.type().msgId();
        
        if (msgId.contains("player_attack") || msgId.contains("mob_attack")) {
            var item = player.getMainHandItem();
            if (!item.isEmpty()) {
                var paths = item.getTags().map(t -> t.location().getPath()).toList();
                if (paths.stream().anyMatch(t -> t.contains("slash") || t.contains("sword") || t.contains("axe"))) return "slash";
                if (paths.stream().anyMatch(t -> t.contains("piercing") || t.contains("trident") || t.contains("arrow"))) return "piercing";
            }
            return "blunt";
        }
        
        return switch (msgId) {
            case "fall", "cactus", "anvil", "falling_block", "fly_into_wall" -> "blunt";
            case "in_fire", "on_fire", "lava", "hot_floor", "fireworks" -> "heat";
            case "lightning_bolt" -> "shock";
            case "arrow", "trident", "thorns", "magic", "indirect_magic" -> "piercing";
            case "poison", "wither", "dragon_breath" -> "poison";
            case "freeze" -> "cold";
            default -> "blunt";
        };
    }
    
    private static void applySlowdown(Player player, double totalDamage) {
        player.removeEffect(MobEffects.SLOWNESS);
        if (totalDamage >= 80) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 4, false, false));
        } else if (totalDamage >= 60) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 2, false, false));
        }
    }
    
    private static void checkGibbing(Player player, net.minecraft.nbt.CompoundTag nbt) {
        double blunt = nbt.getDouble("ssc14_dmg_blunt").orElse(0.0);
        double cellular = nbt.getDouble("ssc14_dmg_cellular").orElse(0.0);
        double heat = nbt.getDouble("ssc14_dmg_heat").orElse(0.0);
        
        if (blunt > 400 || cellular > 200) {
            if (!nbt.getBoolean("ssc14_gibbed").orElse(false)) {
                nbt.putBoolean("ssc14_gibbed", true);
                player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
                System.out.println("[SSC14] 💥 РАСЧЛЕНЕНИЕ: " + player.getName().getString());
            }
        }
        if (heat > 1500) {
            if (!nbt.getBoolean("ssc14_ash").orElse(false)) {
                nbt.putBoolean("ssc14_ash", true);
                player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
                System.out.println("[SSC14] 🔥 ПЕПЕЛ: " + player.getName().getString());
            }
        }
    }
}
