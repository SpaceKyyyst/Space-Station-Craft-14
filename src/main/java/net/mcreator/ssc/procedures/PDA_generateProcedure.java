
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class PDA_generateProcedure {
	
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		
        // ============================================
        // ЛОГИКА ГЕНЕРАЦИИ КПК 
        // ============================================
        if (false == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("generate", false)) {
            {
                final String _tagName = "generate";
                final boolean _tagValue = true;
                CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
            }
            // Начало заполнения инвентаря КПК
            if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
                ItemStack _setstack = new ItemStack(Ssc14ModItems.PEN.get()).copy();
                _setstack.setCount(1);
                _modHandlerItemSetSlot.setStackInSlot(1, _setstack);
            }
			// Если данный предмет = КПК:
			if (Ssc14ModItems.PD_APASSANGER.get() == itemstack.getItem()) {
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.ID_CARD_PASSANGER.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
				}
			} /* else if (>>>ВСТАВИТЬ УСЛОВИЕ С ПЕРВОГО ПРИМЕРА И ЗАМЕНИТЬ НА ДОБАВЛЕННЫЙ КПК<<<) {
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.>>>АЙДИ_НУЖНОЙ_КАРТЫ<<<.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
			} */
			// Конец заполнения
        }

        // ============================================
        // ЛОГИКА ФОНАРИКА: только ВКЛЮЧЕНИЕ и ДВИЖЕНИЕ
        // ============================================
        if (!itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("flashlight", false)) {
            // Фонарик выключен — чистим свет И в игроке, И в предмете (на всякий случай)
            cleanupLight(world, entity, itemstack);
            return;
        }
        
        if (entity == null || world.isClientSide() || !entity.isAlive()) return;
        if (!(entity instanceof LivingEntity living)) return;

        BlockPos currentPos = living.blockPosition();
        var nbt = entity.getPersistentData();
        String key = "ssc14_light_pos_";

        // === Читаем позицию: приоритет у предмета (для синхронизации), потом у игрока ===
        BlockPos lastPos = readLightPos(itemstack, key); // Сначала пробуем из предмета
        if (lastPos == null && nbt.contains(key + "x")) { // Если нет — из игрока
            lastPos = new BlockPos(
                nbt.getInt(key + "x").orElse(0),
                nbt.getInt(key + "y").orElse(0),
                nbt.getInt(key + "z").orElse(0)
            );
        }

        // Оптимизация: не переставляем, если игрок не сдвинулся
        if (lastPos != null && lastPos.equals(currentPos)) {
            // Но всё равно синхронизируем позицию в предмет (на случай дропа)
            writeLightPosToItem(itemstack, currentPos, key);
            return;
        }

        // Удаляем старый блок света
        safeRemoveLight(world, lastPos);

        // Ставим новый блок света (уровень 3) — ТОЛЬКО в воздух или в наш же старый свет
        var currentState = world.getBlockState(currentPos);
        if (currentState.isAir() || currentState.is(Blocks.LIGHT)) {
            BlockState lightState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 3);
            world.setBlock(currentPos, lightState, 3);
        }

        // Запоминаем позицию: И в игроке, И в предмете (двунаправленная синхронизация!)
        nbt.putInt(key + "x", currentPos.getX());
        nbt.putInt(key + "y", currentPos.getY());
        nbt.putInt(key + "z", currentPos.getZ());
        writeLightPosToItem(itemstack, currentPos, key); // <-- КЛЮЧЕВОЕ: синхронизация в предмет
    }
    
    /**
     * Универсальная очистка света (для выключения или подбора)
     */
    private static void cleanupLight(LevelAccessor world, Entity entity, ItemStack itemstack) {
        String key = "ssc14_light_pos_";
        var nbt = entity.getPersistentData();
        
        // Пробуем прочитать позицию из предмета (приоритет)
        BlockPos pos = readLightPos(itemstack, key);
        if (pos == null && nbt.contains(key + "x")) {
            pos = new BlockPos(
                nbt.getInt(key + "x").orElse(0),
                nbt.getInt(key + "y").orElse(0),
                nbt.getInt(key + "z").orElse(0)
            );
        }
        
        safeRemoveLight(world, pos);
        
        // Чистим И в игроке, И в предмете
        nbt.remove(key + "x");
        nbt.remove(key + "y");
        nbt.remove(key + "z");
        clearLightPosInItem(itemstack, key);
    }
    
    /**
     * Чтение позиции из NBT предмета
     */
    private static BlockPos readLightPos(ItemStack stack, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!nbt.contains(key + "x")) return null;
        return new BlockPos(
            nbt.getInt(key + "x").orElse(0),
            nbt.getInt(key + "y").orElse(0),
            nbt.getInt(key + "z").orElse(0)
        );
    }
    
    /**
     * Запись позиции в NBT предмета
     */
    private static void writeLightPosToItem(ItemStack stack, BlockPos pos, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.putInt(key + "x", pos.getX());
        nbt.putInt(key + "y", pos.getY());
        nbt.putInt(key + "z", pos.getZ());
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    
    /**
     * Очистка позиции в NBT предмета
     */
    private static void clearLightPosInItem(ItemStack stack, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.remove(key + "x");
        nbt.remove(key + "y");
        nbt.remove(key + "z");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    
    /**
     * Безопасное удаление света
     */
    private static void safeRemoveLight(LevelAccessor world, BlockPos pos) {
        if (pos == null) return;
        if (!world.hasChunk(pos.getX() >> 4, pos.getZ() >> 4)) return;
        if (world.getBlockState(pos).is(Blocks.LIGHT)) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}