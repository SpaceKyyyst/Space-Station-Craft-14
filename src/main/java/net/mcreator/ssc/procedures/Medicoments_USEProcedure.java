
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

import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber
public class Medicoments_USEProcedure {

    // 🔧 ИСПОЛЬЗУЕМ СТРОКИ ВМЕСТО ПРЕДМЕТОВ, ЧТОБЫ ИЗБЕЖАТЬ КРАША ПРИ ЗАГРУЗКЕ
    private static final Map<String, Map<String, Double>> MEDICAMENT_EFFECTS = new HashMap<>() {{
        // Формат: "modid:item_name" -> Map<"тип_урона", количество_лечения>
        put("ssc_14:brutepack", Map.of("slash", 5.0, "blunt", 5.0, "piercing", 5.0));
        put("ssc_14:bloodpack", Map.of("bloodloss", 0.5));
        put("ssc_14:gauze", Map.of("slash", 5.0, "piercing", 10.0));
        put("ssc_14:ointment", Map.of("heat", 5.0, "shock", 5.0, "cold", 5.0, "caustic", 1.5));
    }};

    private static final int STEPS = 6;
    private static final int STEP_DELAY_TICKS = 7;
    private static final String POS_ATTR = "ssc14_startPos";

    // 1. Лечение ДРУГОГО (ПКМ по сущности)
    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        execute(event, event.getLevel(), event.getTarget(), event.getEntity());
    }

    // 2. САМО-ЛЕЧЕНИЕ (ПКМ по воздуху)
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        
        // Проверяем, является ли предмет медикаментом
        String itemId = BuiltInRegistries.ITEM.getKey(heldItem.getItem()).toString();
        if (!MEDICAMENT_EFFECTS.containsKey(itemId)) return;
        
        // Запускаем лечение на себе
        execute(event, event.getLevel(), player, player);
    }

    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) return;
        if (!(entity instanceof LivingEntity target)) return;
        if (!(sourceentity instanceof Player player)) return;
        
        // Проверка: игрок ИЛИ имеет тег alive
        boolean isPlayer = (entity instanceof Player);
        boolean hasTag = entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("ssc_14:alive")));
        
        if (!isPlayer && !hasTag) return;
        
        ItemStack heldItem = player.getMainHandItem();
        String itemId = BuiltInRegistries.ITEM.getKey(heldItem.getItem()).toString();
        
        if (!MEDICAMENT_EFFECTS.containsKey(itemId)) return;
        
        startHealingProcess(player, target, heldItem, MEDICAMENT_EFFECTS.get(itemId));
    }
    
    private static void startHealingProcess(Player player, LivingEntity target, ItemStack itemStack, Map<String, Double> effects) {
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
            return;
        }
        
        if (!canContinueHealing(player, target, itemStack, startPos)) {
            updateProgressBar(target, 0);
            return;
        }
        
        Ssc14Mod.queueServerWork(STEP_DELAY_TICKS, () -> 
            runHealingTimer(player, target, itemStack, effects, currentStep + 1, startPos)
        );
    }
    
    private static boolean canContinueHealing(Player player, LivingEntity target, ItemStack itemStack, double startPos) {
        if (player.getX() + player.getY() + player.getZ() != startPos) return false;
        String itemId = BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).toString();
        if (!MEDICAMENT_EFFECTS.containsKey(itemId)) return false;
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
            
            // Лечим только если есть урон этого типа
            if (currentDamage > 0) {
                double newDamage = Math.max(0, currentDamage - healAmount);
                nbt.putDouble(key, newDamage);
                totalHealed += (currentDamage - newDamage);
                wasAnyHealed = true;
            }
        }
        
        if (wasAnyHealed) {
            double totalBefore = nbt.getDouble("sscCustomHealth").orElse(0.0);
            double totalAfter = Math.max(0, totalBefore - totalHealed);
            nbt.putDouble("sscCustomHealth", totalAfter);
            updateHealthUIAttribute(target, totalAfter);
        }
        
        return wasAnyHealed;
    }
    
    private static void updateHealthUIAttribute(LivingEntity target, double totalDamage) {
        if (target.getAttributes().hasAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE)) {
            var attr = target.getAttribute(Ssc14ModAttributes.HEALTH_U_IATTRIBUTE);
            if (attr != null) {
                int uiState;
                if (totalDamage <= 12) uiState = 0;
                else if (totalDamage <= 37) uiState = 1;
                else if (totalDamage <= 62) uiState = 2;
                else if (totalDamage <= 87) uiState = 3;
                else if (totalDamage <= 100) uiState = 4;
                else if (totalDamage <= 200) uiState = 5;
                else uiState = 6;
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
