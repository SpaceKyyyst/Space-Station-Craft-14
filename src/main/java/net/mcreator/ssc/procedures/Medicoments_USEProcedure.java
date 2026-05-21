
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber
public class Medicoments_USEProcedure {

    private static final Map<String, Map<String, Double>> MEDICAMENT_EFFECTS = new HashMap<>() {{
        put("ssc_14:brutepack", Map.of("slash", 5.0, "blunt", 5.0, "piercing", 5.0));
        put("ssc_14:bloodpack", Map.of("bloodloss", 0.5));
        put("ssc_14:gauze", Map.of("slash", 5.0, "piercing", 10.0));
        put("ssc_14:ointment", Map.of("heat", 5.0, "shock", 5.0, "cold", 5.0, "caustic", 1.5));
    }};

    private static final int STEPS = 6;
    private static final int STEP_DELAY_TICKS = 7;
    private static final String POS_ATTR = "ssc14_startPos";
    private static final String HEALER_TAG = "ssc14_currentHealer";

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        execute(event, event.getLevel(), event.getTarget(), event.getEntity());
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        
        String itemId = getItemId(heldItem.getItem());
        if (!MEDICAMENT_EFFECTS.containsKey(itemId)) return;
        execute(event, event.getLevel(), player, player);
    }

    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) return;
        if (!(entity instanceof LivingEntity target)) return;
        if (!(sourceentity instanceof Player player)) return;
        
        if (!(entity instanceof Player) && 
            !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("ssc_14:alive")))) {
            return;
        }
        
        ItemStack heldItem = player.getMainHandItem();
        String itemId = getItemId(heldItem.getItem());
        
        if (!MEDICAMENT_EFFECTS.containsKey(itemId)) return;
        
        startHealingProcess(player, target, heldItem, MEDICAMENT_EFFECTS.get(itemId));
    }
    
    private static String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
    
    private static boolean hasHealableDamage(LivingEntity target, Map<String, Double> effects) {
        var nbt = target.getPersistentData();
        for (String damageType : effects.keySet()) {
            String key = "ssc14_dmg_" + damageType;
            if (nbt.getDouble(key).orElse(0.0) > 0) return true;
        }
        return false;
    }
    
    private static UUID getCurrentHealer(LivingEntity target) {
        var nbt = target.getPersistentData();
        if (nbt.contains(HEALER_TAG)) {
            try {
                return UUID.fromString(nbt.getString(HEALER_TAG).orElse(""));
            } catch (Exception ignored) {}
        }
        return null;
    }
    
    private static void setCurrentHealer(LivingEntity target, UUID healerUUID) {
        var nbt = target.getPersistentData();
        if (healerUUID != null) {
            nbt.putString(HEALER_TAG, healerUUID.toString());
        } else {
            nbt.remove(HEALER_TAG);
        }
    }
    
    // 🔧 НОВЫЙ МЕТОД: Принудительная отмена лечения
    private static void cancelHealing(LivingEntity target) {
        updateProgressBar(target, 0);
        setCurrentHealer(target, null);
        System.out.println("[SSC14-HEAL] Лечение отменено: " + target.getName().getString());
    }
    
    private static void startHealingProcess(Player player, LivingEntity target, ItemStack itemStack, Map<String, Double> effects) {
        UUID currentHealer = getCurrentHealer(target);
        
        // 🔧 ПРОВЕРКА НА ОТМЕНУ: если тот же игрок кликает повторно → отменяем лечение
        if (currentHealer != null && currentHealer.equals(player.getUUID())) {
            cancelHealing(target);
            return;
        }
        
        // 🔧 Если другой игрок уже лечит → не начинаем новый процесс
        if (currentHealer != null) {
            System.out.println("[SSC14-HEAL] Отмена: " + target.getName().getString() + " уже лечится другим игроком");
            return;
        }
        
        // 🔧 Начинаем новый процесс лечения
        setCurrentHealer(target, player.getUUID());
        
        var playerNbt = player.getPersistentData();
        double startPos = player.getX() + player.getY() + player.getZ();
        playerNbt.putDouble(POS_ATTR, startPos);
        
        runHealingTimer(player, target, itemStack, effects, 0, startPos);
    }
    
    private static void runHealingTimer(Player player, LivingEntity target, ItemStack itemStack, 
                                       Map<String, Double> effects, int currentStep, double startPos) {
        updateProgressBar(target, currentStep);
        
        if (currentStep == STEPS) {
            boolean wasHealed = applyHealing(target, effects);
            if (wasHealed) itemStack.shrink(1);
            updateProgressBar(target, 0);
            
            setCurrentHealer(target, null);
            
            // 🔧 ПРОВЕРКА НА АВТО-ПОВТОР (только если не было отмены)
            if (wasHealed && itemStack.getCount() > 0 && 
                player.getMainHandItem().is(itemStack.getItem()) &&
                hasHealableDamage(target, effects)) {
                Ssc14Mod.queueServerWork(2, () -> 
                    startHealingProcess(player, target, itemStack, effects)
                );
            }
            return;
        }
        
        if (!canContinueHealing(player, target, itemStack, startPos)) {
            cancelHealing(target);
            return;
        }
        
        Ssc14Mod.queueServerWork(STEP_DELAY_TICKS, () -> 
            runHealingTimer(player, target, itemStack, effects, currentStep + 1, startPos)
        );
    }
    
    private static boolean canContinueHealing(Player player, LivingEntity target, ItemStack itemStack, double startPos) {
        if (player.getX() + player.getY() + player.getZ() != startPos) return false;
        if (!MEDICAMENT_EFFECTS.containsKey(getItemId(player.getMainHandItem().getItem()))) return false;
        return true;
    }
    
    private static boolean applyHealing(LivingEntity target, Map<String, Double> effects) {
        var nbt = target.getPersistentData();
        boolean wasAnyHealed = false;
        double totalHealed = 0.0;
        
        for (Map.Entry<String, Double> entry : effects.entrySet()) {
            String damageType = entry.getKey();
            double healAmount = entry.getValue();
            String key = "ssc14_dmg_" + damageType;
            
            double currentDamage = nbt.getDouble(key).orElse(0.0);
            
            if (currentDamage > 0) {
                double newDamage = Math.max(0, currentDamage - healAmount);
                totalHealed += (currentDamage - newDamage);
                nbt.putDouble(key, newDamage);
                wasAnyHealed = true;
            }
        }
        
        if (wasAnyHealed) {
            double totalBefore = nbt.getDouble("sscCustomHealth").orElse(0.0);
            double totalAfter = Math.max(0, totalBefore - totalHealed);
            nbt.putDouble("sscCustomHealth", totalAfter);
            
            updateHealthUIAttribute(target, totalAfter);
            
            if (target instanceof Player player) {
                updateSlowdownAttribute(player, totalAfter);
            }
            
            System.out.println("[SSC14-HEAL] Всего: " + totalBefore + " → " + totalAfter + " | UI & скорость обновлены");
        }
        
        return wasAnyHealed;
    }
    
    private static void updateSlowdownAttribute(Player player, double totalDamage) {
        if (player.isSpectator() || player.isCreative()) return;
        
        var attr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attr == null) return;
        
        ResourceLocation slowId = ResourceLocation.parse("ssc14:slowdown");
        
        if (attr.hasModifier(slowId)) {
            attr.removeModifier(slowId);
        }
        
        if (totalDamage >= 80.0) {
            attr.addTransientModifier(new AttributeModifier(slowId, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        } else if (totalDamage >= 60.0) {
            attr.addTransientModifier(new AttributeModifier(slowId, -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }
    
    private static void updateHealthUIAttribute(LivingEntity target, double totalDamage) {
        if (target.getAttributes().hasAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE)) {
            var attr = target.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
            if (attr != null) {
                int uiState = totalDamage <= 12 ? 0 : totalDamage <= 37 ? 1 : totalDamage <= 62 ? 2 : 
                              totalDamage <= 87 ? 3 : totalDamage <= 100 ? 4 : totalDamage <= 200 ? 5 : 6;
                attr.setBaseValue(uiState);
            }
        }
    }
    
    private static void updateProgressBar(LivingEntity target, int step) {
        if (target.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) {
            target.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);
        }
    }
}
