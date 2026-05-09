package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.GameType;

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.init.Ssc14ModEntities;

import javax.annotation.Nullable;

@EventBusSubscriber
public class OnPlayerTakeDamageSSC14Procedure {

    private static final String KEY_TOTAL_DAMAGE = "sscCustomHealth";
    private static final String KEY_BLEEDING = "ssc14_bleeding";
    private static final String KEY_GIBBED = "ssc14_gibbed";
    private static final String KEY_ASH = "ssc14_ash";
    private static final String KEY_CRITICAL = "ssc14_critical";
    private static final String PREFIX_TYPE = "ssc14_dmg_";
    
    private static final ResourceLocation SSC14_SLOWDOWN_ID = ResourceLocation.parse("ssc14:slowdown");
    private static final ResourceLocation CRIT_IMMOBILIZE_ID = ResourceLocation.parse("ssc14:crit_immobilize");
    private static final ResourceLocation CRIT_NOJUMP_ID = ResourceLocation.parse("ssc14:crit_nojump");

    private static final double SLOW_60 = 60.0;
    private static final double SLOW_80 = 80.0;
    private static final double BLEED_THRESHOLD = 10.0;
    private static final double GIB_BLUNT = 400.0;
    private static final double GIB_CELLULAR = 200.0;
    private static final double GIB_HEAT = 1500.0;
    private static final double CRIT_MIN = 100.0;
    private static final double CRIT_MAX = 200.0;

    @SubscribeEvent
    public static void onEntityAttacked(LivingDamageEvent.Pre event) {
        execute(event, event.getEntity().level(), event.getEntity());
    }

    public static void execute(LevelAccessor world, Entity entity) { execute(null, world, entity); }

    private static void execute(@Nullable net.neoforged.bus.api.Event event, LevelAccessor world, Entity entity) {
        if (!(event instanceof LivingDamageEvent.Pre damageEvent)) return;
        if (!(entity instanceof Player player)) return;
        if (world.isClientSide()) return;

        damageEvent.setNewDamage(0);
        CompoundTag nbt = player.getPersistentData();
        float incoming = damageEvent.getOriginalDamage();
        DamageSource source = damageEvent.getSource();

        String damageType = mapDamageType(source, player);
        String typeKey = PREFIX_TYPE + damageType;
        double typeDamage = nbt.getDouble(typeKey).orElse(0.0) + incoming;
        nbt.putDouble(typeKey, typeDamage);
        
        double totalDamage = nbt.getDouble(KEY_TOTAL_DAMAGE).orElse(0.0) + incoming;
        nbt.putDouble(KEY_TOTAL_DAMAGE, totalDamage);
        
        System.out.println("[SSC14] +" + incoming + " " + damageType + " | Всего: " + totalDamage);

        updateHealthUIAttribute(player, totalDamage);
        updateCriticalState(player, nbt, totalDamage);
        applySlowdownAttribute(player, totalDamage);
        
        // 🔧 ПРОВЕРКА СМЕРТИ — ДО checkGibbing, чтобы не было конфликта
        if (totalDamage >= CRIT_MAX && !nbt.getBoolean("ssc14_dead").orElse(false)) {
            System.out.println("[SSC14-DEBUG] Попытка смерти: урон=" + totalDamage + " | dead=" + nbt.getBoolean("ssc14_dead").orElse(false));
            nbt.putBoolean("ssc14_dead", true);
            handleDeath(player, nbt);
            return; // 🔹 Прерываем, чтобы checkGibbing не сработал после смерти
        }
        
        checkGibbing(player, nbt);
        
        if (damageType.equals("slash") && typeDamage > BLEED_THRESHOLD) {
            if (!nbt.getBoolean(KEY_BLEEDING).orElse(false)) {
                nbt.putBoolean(KEY_BLEEDING, true);
                System.out.println("[SSC14] 🩸 Кровотечение начато");
            }
        }
    }

	// 🔧 ОБРАБОТКА СМЕРТИ (минималистичная версия)
	private static void handleDeath(Player player, CompoundTag nbt) {
	    if (player.level().isClientSide()) return;
	    
	    try {
	        System.out.println("[SSC14-DEBUG] handleDeath: START | player=" + player.getName().getString());
	        
	        if (!(player.level() instanceof net.minecraft.server.level.ServerLevel serverLevel)) {
	            System.err.println("[SSC14-ERROR] handleDeath: level is not ServerLevel!");
	            return;
	        }
	        
	        // 🔧 Создаём труп через конструктор
	        net.mcreator.ssc.entity.CorpseEntity corpse = new net.mcreator.ssc.entity.CorpseEntity(
	            net.mcreator.ssc.init.Ssc14ModEntities.CORPSE.get(), 
	            serverLevel
	        );
	        
	        corpse.setPos(player.position());
	        corpse.setPose(net.minecraft.world.entity.Pose.SWIMMING);
	        corpse.refreshDimensions(); // 🔹 Применяем размеры
	        
	        // 🔧 Копируем только SSC14 данные (урон, флаги)
	        corpse.setOriginalPlayerData(player.getUUID(), nbt.getDouble("sscCustomHealth").orElse(0.0));
	        corpse.getPersistentData().put("SSC14_HealthData", nbt.copy());
	        
	        // 🔧 Добавляем в мир
	        boolean added = serverLevel.addFreshEntity(corpse);
	        System.out.println("[SSC14-DEBUG] addFreshEntity=" + added + " | corpse UUID=" + corpse.getUUID());
	        
	        if (!added) {
	            System.err.println("[SSC14-ERROR] handleDeath: failed to add corpse to world!");
	            return;
	        }
	        
	        // 🔧 Очищаем инвентарь игрока (предметы можно добавить в труп позже)
	        player.getInventory().clearContent();
	        player.getInventory().setChanged();
	        
	        // 🔧 Переключаем в спектатор
	        if (player instanceof net.minecraft.server.level.ServerPlayer sp) {
	            sp.setGameMode(net.minecraft.world.level.GameType.SPECTATOR);
	            System.out.println("[SSC14] ☠️ СМЕРТЬ: " + sp.getName().getString() + " → SPECTATOR");
	        }
	        
	    } catch (Exception e) {
	        System.err.println("[SSC14-ERROR] handleDeath exception: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    // === ОСТАЛЬНЫЕ МЕТОДЫ ===
    private static void updateCriticalState(Player player, CompoundTag nbt, double totalDamage) {
        boolean wasCritical = nbt.getBoolean(KEY_CRITICAL).orElse(false);
        boolean shouldBeCritical = (totalDamage >= CRIT_MIN && totalDamage < CRIT_MAX);
        
        if (shouldBeCritical && !wasCritical) {
            nbt.putBoolean(KEY_CRITICAL, true);
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
            System.out.println("[SSC14] 🚨 КРИТ: " + player.getName().getString());
        } else if (!shouldBeCritical && wasCritical) {
            nbt.putBoolean(KEY_CRITICAL, false);
            player.removeEffect(MobEffects.BLINDNESS);
            removeCriticalModifiers(player);
            System.out.println("[SSC14] 💚 ВЫХОД ИЗ КРИТА: " + player.getName().getString());
        }
    }

    private static void removeCriticalModifiers(Player player) {
        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && speedAttr.hasModifier(CRIT_IMMOBILIZE_ID)) speedAttr.removeModifier(CRIT_IMMOBILIZE_ID);
        var jumpAttr = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpAttr != null && jumpAttr.hasModifier(CRIT_NOJUMP_ID)) jumpAttr.removeModifier(CRIT_NOJUMP_ID);
    }

    private static void updateHealthUIAttribute(Player player, double totalDamage) {
        var attr = player.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
        if (attr == null) return;
        int uiState = totalDamage <= 12 ? 0 : totalDamage <= 37 ? 1 : totalDamage <= 62 ? 2 : 
                      totalDamage <= 87 ? 3 : totalDamage <= 100 ? 4 : totalDamage <= 200 ? 5 : 6;
        attr.setBaseValue(uiState);
    }

    private static String mapDamageType(DamageSource source, Player player) {
        String msgId = source.type().msgId();
        if (msgId.startsWith("ssc_14dmg")) return switch (msgId) {
            case "ssc_14dmgblunt" -> "blunt"; case "ssc_14dmgslash" -> "slash";
            case "ssc_14dmgpiercing" -> "piercing"; case "ssc_14dmgheat" -> "heat";
            case "ssc_14dmgshock" -> "shock"; case "ssc_14dmgcold" -> "cold";
            case "ssc_14dmgcaustic" -> "caustic"; case "ssc_14dmgpoison" -> "poison";
            case "ssc_14dmgradiation" -> "radiation"; case "ssc_14dmgasphyx" -> "asphyx";
            case "ssc_14dmgbloodloss" -> "bloodloss"; case "ssc14dmgcellular" -> "cellular";
            default -> "blunt";
        };
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

    private static void applySlowdownAttribute(Player player, double totalDamage) {
        if (player.isSpectator() || player.isCreative()) { removeSlowdownModifier(player); return; }
        var attr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attr == null) return;
        removeSlowdownModifier(player);
        if (totalDamage >= SLOW_80) attr.addTransientModifier(new AttributeModifier(SSC14_SLOWDOWN_ID, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        else if (totalDamage >= SLOW_60) attr.addTransientModifier(new AttributeModifier(SSC14_SLOWDOWN_ID, -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    private static void removeSlowdownModifier(Player player) {
        var attr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attr != null && attr.hasModifier(SSC14_SLOWDOWN_ID)) attr.removeModifier(SSC14_SLOWDOWN_ID);
    }

    private static void checkGibbing(Player player, CompoundTag nbt) {
        double blunt = nbt.getDouble(PREFIX_TYPE + "blunt").orElse(0.0);
        double cellular = nbt.getDouble(PREFIX_TYPE + "cellular").orElse(0.0);
        double heat = nbt.getDouble(PREFIX_TYPE + "heat").orElse(0.0);
        if (blunt > GIB_BLUNT || cellular > GIB_CELLULAR) {
            if (!nbt.getBoolean(KEY_GIBBED).orElse(false)) { 
                nbt.putBoolean(KEY_GIBBED, true); 
                player.hurt(player.damageSources().generic(), Float.MAX_VALUE); 
                System.out.println("[SSC14] 💥 ГИББИНГ: " + player.getName().getString());
            }
        }
        if (heat > GIB_HEAT) {
            if (!nbt.getBoolean(KEY_ASH).orElse(false)) { 
                nbt.putBoolean(KEY_ASH, true); 
                player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
                System.out.println("[SSC14] 🔥 ПЕПЕЛ: " + player.getName().getString());
            }
        }
    }
}
