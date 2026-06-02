
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

import net.mcreator.ssc.init.Ssc14ModItems;

@EventBusSubscriber
public class PDApassanger_Drop_PR_Procedure {
    
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null || !(entity instanceof ItemEntity itemEntity)) return;
        
        ItemStack stack = itemEntity.getItem();
        
        // === 1. Проверяем, что это наш КПК ===
        if (stack.getItem() != Ssc14ModItems.PD_APASSANGER.get()) {
            return;
        }
        
        boolean isFlashlightOn = stack.getOrDefault(
            DataComponents.CUSTOM_DATA, 
            CustomData.EMPTY
        ).copyTag().getBooleanOr("flashlight", false);
        
        BlockPos currentPos = itemEntity.blockPosition();
        var level = itemEntity.level();
        
        if (level.isClientSide()) return;
        
        String key = "ssc14_light_pos_";
        var itemNbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        
        // === 2. Читаем позицию из NBT предмета (теперь там всегда есть данные!) ===
        BlockPos lastPos = null;
        if (itemNbt.contains(key + "x")) {
            lastPos = new BlockPos(
                itemNbt.getInt(key + "x").orElse(0),
                itemNbt.getInt(key + "y").orElse(0),
                itemNbt.getInt(key + "z").orElse(0)
            );
        }
        
        // === 3. Фонарик ВЫКЛЮЧЕН — удаляем свет ===
        if (!isFlashlightOn) {
            safeRemoveLight(level, lastPos);
            clearLightPos(itemNbt, stack, key);
            return;
        }
        
        // === 4. Фонарик ВКЛЮЧЁН — ставим/двигаем свет ===
        
        // Оптимизация: не переставляем, если не сдвинулся
        if (lastPos != null && lastPos.equals(currentPos)) return;
        
        // Удаляем старый блок света
        safeRemoveLight(level, lastPos);
        
        // === 5. Ставим новый свет — ТОЛЬКО в воздух или в наш же старый свет ===
        var currentState = level.getBlockState(currentPos);
        if (currentState.isAir() || currentState.is(Blocks.LIGHT)) {
            BlockState lightState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 3);
            level.setBlock(currentPos, lightState, 3);
        }
        
        // === 6. Запоминаем позицию в NBT предмета ===
        writeLightPos(itemNbt, currentPos, stack, key);
    }
    
    private static void safeRemoveLight(LevelAccessor level, BlockPos pos) {
        if (pos == null) return;
        if (!level.hasChunk(pos.getX() >> 4, pos.getZ() >> 4)) return;
        if (level.getBlockState(pos).is(Blocks.LIGHT)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
    
    private static void writeLightPos(CompoundTag nbt, BlockPos pos, ItemStack stack, String key) {
        nbt.putInt(key + "x", pos.getX());
        nbt.putInt(key + "y", pos.getY());
        nbt.putInt(key + "z", pos.getZ());
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    
    private static void clearLightPos(CompoundTag nbt, ItemStack stack, String key) {
        nbt.remove(key + "x");
        nbt.remove(key + "y");
        nbt.remove(key + "z");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
}
