
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.Ssc14Mod;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.ILevelExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MicrwGUIbutEProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "on_off") instanceof BooleanProperty _getbp1 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp1)
                && !(getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "active") instanceof BooleanProperty _getbp3 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp3))) {
            
            // === ЗВУК ЗАПУСКА МИКРОВОЛНОВКИ ===
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:microwave_start_beep")), SoundSource.BLOCKS, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:microwave_start_beep")), SoundSource.BLOCKS, 1, 1, false);
                }
            }
            
            if (!world.isClientSide()) {
                BlockPos _bp = BlockPos.containing(x, y, z);
                BlockEntity _blockEntity = world.getBlockEntity(_bp);
                BlockState _bs = world.getBlockState(_bp);
                
                if (_blockEntity != null) {
                    _blockEntity.getPersistentData().putBoolean("active", true);
                }
                
                // === ПРАВКА 4: Устанавливаем блокстейт "active" в true ===
                {
                    BlockPos _pos = BlockPos.containing(x, y, z);
                    BlockState _bsState = world.getBlockState(_pos);
                    if (_bsState.getBlock().getStateDefinition().getProperty("active") instanceof BooleanProperty _booleanProp)
                        world.setBlock(_pos, _bsState.setValue(_booleanProp, true), 3);
                }
                
                if (world instanceof Level _level)
                    _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                
                // запуск нормально отображаемого таймера для GUI
                double currentTimer = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer");
                if (_blockEntity != null) {
                    _blockEntity.getPersistentData().putDouble("timer_2", currentTimer);
                }
                if (world instanceof Level _level)
                    _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                
                MicrwGUItimerPRProcedure.execute(world, x, y, z);
                
                // === НОВАЯ ЛОГИКА ЦИКЛИЧЕСКОГО ЗВУКА ===
                // Если таймер больше 1 секунды, запускаем циклический звук
                if (currentTimer > 1) {
                    // Вычисляем количество повторений: таймер / 5
                    int repeatCount = (int)(currentTimer / 5);
                    if (repeatCount > 0) {
                        playLoopSoundWithCount(world, x, y, z, repeatCount);
                    }
                }
            }
            
            Ssc14Mod.queueServerWork((int) (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer") * 20), () -> {
                if (Ssc14ModBlocks.MICROWAWE.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
                    if (!world.isClientSide()) {
                        BlockPos _bp = BlockPos.containing(x, y, z);
                        BlockEntity _blockEntity = world.getBlockEntity(_bp);
                        BlockState _bs = world.getBlockState(_bp);
                        
                        // Сохраняем значение таймера ПЕРЕД обнулением
                        int savedTimer = (int) Math.round(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer"));
                        
                        if (_blockEntity != null) {
                            _blockEntity.getPersistentData().putBoolean("active", false);
                            _blockEntity.getPersistentData().putDouble("timer", 0);
                        }
                        
                        // === ПРАВКА 4: Возвращаем блокстейт "active" в false ===
                        {
                            BlockPos _pos = BlockPos.containing(x, y, z);
                            BlockState _bsState = world.getBlockState(_pos);
                            if (_bsState.getBlock().getStateDefinition().getProperty("active") instanceof BooleanProperty _booleanProp)
                                world.setBlock(_pos, _bsState.setValue(_booleanProp, false), 3);
                        }
                        
                        if (world instanceof Level _level)
                            _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                        
                        // === ЗВУК ЗАВЕРШЕНИЯ ПРИГОТОВЛЕНИЯ ===
                        if (world instanceof Level _level) {
                            if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:microwave_done_beep")), SoundSource.BLOCKS, 1, 1);
                            } else {
                                _level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:microwave_done_beep")), SoundSource.BLOCKS, 1, 1, false);
                            }
                        }
                        
                        // Код анализа всех предметов в микроволновке и выдачи готовой продукции
                        processMicrowaveRecipes(world, x, y, z, savedTimer);
                    }
                }
            });
        }
    }

    // === НОВЫЙ МЕТОД ДЛЯ ЦИКЛИЧЕСКОГО ЗВУКА С ФИКСИРОВАННЫМ КОЛИЧЕСТВОМ ПОВТОРЕНИЙ ===
    private static void playLoopSoundWithCount(LevelAccessor world, double x, double y, double z, int remainingCount) {
        if (remainingCount <= 0) return;
        
        if (!world.isClientSide() && world instanceof Level _level) {
            // Воспроизводим звук гудения
            _level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:microwave_loop_6")), SoundSource.BLOCKS, 1, 1);
            
            // Если остались ещё повторения, планируем следующий через 5 секунд (100 тиков)
            if (remainingCount > 1) {
                Ssc14Mod.queueServerWork(100, () -> {
                    playLoopSoundWithCount(world, x, y, z, remainingCount - 1);
                });
            }
        }
    }

    private static void processMicrowaveRecipes(LevelAccessor world, double x, double y, double z, int timer) {
        // Получаем capability инвентаря блока через ILevelExtension
        if (!(world instanceof ILevelExtension _ext)) return;
        
        IItemHandler handler = _ext.getCapability(Capabilities.ItemHandler.BLOCK, BlockPos.containing(x, y, z), null);
        if (handler == null) return;
        
        // Пытаемся получить модифицируемый инвентарь для очистки слотов
        IItemHandlerModifiable modifiableHandler = null;
        if (handler instanceof IItemHandlerModifiable) {
            modifiableHandler = (IItemHandlerModifiable) handler;
        }
        
        // Копируем все предметы из слотов микроволновки (1-12) в список, чтобы работать с ними напрямую
        // Это необходимо, так как нам важно сохранять конкретные ItemStack (с их компонентами жидкостей внутри)
        List<ItemStack> microwaveContents = new ArrayList<>();
        for (int slotId = 1; slotId <= 12; slotId++) {
            ItemStack stack = handler.getStackInSlot(slotId);
            if (!stack.isEmpty()) {
                microwaveContents.add(stack.copy());
            }
        }
        
        // Карта для готовой продукции, которую микроволновка выдаст в конце
        Map<Item, Integer> spawnItems = new HashMap<>();

        // =========================================================================
        // === СИСТЕМА РЕЦЕПТОВ МИКРОВОЛНОВКИ ===
        // =========================================================================

        /*
        // ШАБЛОН 1: [ЖИДКОСТЬ] + [ПРЕДМЕТ] -> [ [ВРЕМЯ] сек ] -> [ИТОГ]
        if (timer == [ВРЕМЯ]]) {
            int ingredientCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.ПРЕДМЕТ.get()) {   // << ТУТ ПРЕДМЕТ
                    ingredientCount += stack.getCount();
                }
            }

            int totalFluidAvailable = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                    totalFluidAvailable += net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("ЖИДКОСТЬ", 0);   // << ТУТ ЖИДКОСТЬ
                }
            }

            // Сколько единиц жидкости тратится на ОДНУ порцию крафта (например, 5u)
            int fluidCostPerCraft = 5; 

            int possibleCraftsByFluid = totalFluidAvailable / fluidCostPerCraft;
            int canCraft = Math.min(ingredientCount, possibleCraftsByFluid);

            if (canCraft > 0) {
                // Списываем твёрдый ингредиент (замените ПРЕДМЕТ на свой предмет)   // << ТУТ ПРЕДМЕТ
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.ПРЕДМЕТ.get(), canCraft);

                int fluidToRemove = canCraft * fluidCostPerCraft;
                for (ItemStack stack : microwaveContents) {
                    if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                        int fluidInContainer = net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("ЖИДКОСТЬ", 0);   // << ТУТ ЖИДКОСТЬ
                        if (fluidInContainer > 0) {
                            int toTake = Math.min(fluidToRemove, fluidInContainer);
                            net.mcreator.ssc.ModReagents.removeReagent(stack, "ЖИДКОСТЬ", toTake);   // << ТУТ ТОЖЕ ЖИДКОСТЬ
                            fluidToRemove -= toTake;
                            if (fluidToRemove <= 0) break; 
                        }
                    }
                }

                // ЗАМЕНИТЬ "[ИТОГ]" НА НУЖНЫЙ ПРЕДМЕТ
                spawnItems.merge(Ssc14ModItems.[ИТОГ].get(), canCraft, Integer::sum);
            }
        }


        // ШАБЛОН 2: [ПРЕДМЕТ_1] + [ПРЕДМЕТ_2] + [ПРЕДМЕТ_3] -> [ [ВРЕМЯ] сек ] -> [ИТОГ]
        if (timer == [ВРЕМЯ]) {
            int ing1Count = 0; // Для первого предмета
            int ing2Count = 0; // Для второго предмета
            int ing3Count = 0; // Для третьего предмета (если не нужен, удалите строчки с ing3)

            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.[ПРЕДМЕТ_1].get()) ing1Count += stack.getCount();
                if (stack.getItem() == Ssc14ModItems.[ПРЕДМЕТ_2].get()) ing2Count += stack.getCount();
                if (stack.getItem() == Ssc14ModItems.[ПРЕДМЕТ_3].get()) ing3Count += stack.getCount();
            }
            
            int canCraft = Math.min(ing1Count, Math.min(ing2Count, ing3Count));    // ПОДРЕДАКТИРОВАТЬ
            
            if (canCraft > 0) {
                // Списываем все использованные ингредиенты
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.[ПРЕДМЕТ_1].get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.[ПРЕДМЕТ_2].get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.[ПРЕДМЕТ_3].get(), canCraft);
                
                // Выдаём результат (замените [ИТОГ] на итоговое блюдо)
                spawnItems.merge(Ssc14ModItems.[ИТОГ].get(), canCraft, Integer::sum);
            }
        }
         */

        // РЕЦЕПТ №1: Соль 5u + Картофель -> [ 15 сек ] -> Космический фри
        if (timer == 15) {
            // Считаем, сколько обычного картофеля лежит в печке
            int potatoCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.POTATO.get()) {
                    potatoCount += stack.getCount();
                }
            }

            // Считаем общее количество доступной соли "table_salt" во ВСЕХ контейнерах внутри печки
            int totalSaltAvailable = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                    totalSaltAvailable += net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("table_salt", 0);
                }
            }

            // Вычисляем, сколько порций фри мы можем приготовить (на 1 фри нужно: 1 картошка и 5 единиц соли)
            int possibleCraftsBySalt = totalSaltAvailable / 5;
            int canCraft = Math.min(potatoCount, possibleCraftsBySalt);

            if (canCraft > 0) {
                // 1. Списываем картофель из списка содержимого
                int potatoToRemove = canCraft;
                for (ItemStack stack : microwaveContents) {
                    if (stack.getItem() == Ssc14ModItems.POTATO.get()) {
                        if (stack.getCount() >= potatoToRemove) {
                            stack.shrink(potatoToRemove);
                            break;
                        } else {
                            potatoToRemove -= stack.getCount();
                            stack.setCount(0);
                        }
                    }
                }

                // 2. Списываем ровно по 5u соли за каждую порцию из контейнеров реагентов
                int saltToRemove = canCraft * 5;
                for (ItemStack stack : microwaveContents) {
                    if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                        int saltInContainer = net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("table_salt", 0);
                        if (saltInContainer > 0) {
                            int toTake = Math.min(saltToRemove, saltInContainer);
                            net.mcreator.ssc.ModReagents.removeReagent(stack, "table_salt", toTake);
                            saltToRemove -= toTake;
                            if (saltToRemove <= 0) break; // Вся нужная соль успешно списана
                        }
                    }
                }

                // 3. Добавляем готовый Космический фри в список выдачи
                spawnItems.merge(Ssc14ModItems.SPACE_FRIES.get(), canCraft, Integer::sum);
            }
        }

        // Рецепт №2: Булочка + Сыр + Мясо -> [ 10 сек ] -> Чизбургер
        if (timer == 10) {
            int bunCount = 0, cheeseCount = 0, meatCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.BUN.get()) bunCount += stack.getCount();
                if (stack.getItem() == Ssc14ModItems.CHEESE.get()) cheeseCount += stack.getCount();
                if (stack.getItem() == Ssc14ModItems.MEAT.get()) meatCount += stack.getCount();
            }
            
            int canCraft = Math.min(bunCount, Math.min(cheeseCount, meatCount));
            if (canCraft > 0) {
                // Списываем ингредиенты
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.BUN.get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.CHEESE.get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.MEAT.get(), canCraft);
                // Выдаем результат
                spawnItems.merge(Ssc14ModItems.CHEESEBURGER.get(), canCraft, Integer::sum);
            }
        }
        
        // Рецепт №3: BUN + TOFU_SLICE -> [ 10 сек ] -> TOFU_BURGER
        if (timer == 10) {
            int bunCount = 0, tofuCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.BUN.get()) bunCount += stack.getCount();
                if (stack.getItem() == Ssc14ModItems.TOFU_SLICE.get()) tofuCount += stack.getCount();
            }
            int canCraft = Math.min(bunCount, tofuCount);
            if (canCraft > 0) {
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.BUN.get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.TOFU_SLICE.get(), canCraft);
                spawnItems.merge(Ssc14ModItems.TOFU_BURGER.get(), canCraft, Integer::sum);
            }
        }
        
        // Рецепт №4: DOUGH -> [ 10 сек ] -> BREAD
        if (timer == 10) {
            int doughCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.DOUGH.get()) doughCount += stack.getCount();
            }
            if (doughCount > 0) {
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.DOUGH.get(), doughCount);
                spawnItems.merge(Ssc14ModItems.BREAD.get(), doughCount, Integer::sum);
            }
        }
        
        // Рецепт №5: DOUGH_PIECE -> [ 5 сек ] -> BUN
        if (timer == 5) {
            int doughPieceCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.DOUGH_PIECE.get()) doughPieceCount += stack.getCount();
            }
            if (doughPieceCount > 0) {
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.DOUGH_PIECE.get(), doughPieceCount);
                spawnItems.merge(Ssc14ModItems.BUN.get(), doughPieceCount, Integer::sum);
            }
        }

        // =========================================================================
        // === ФИНАЛЬНАЯ ВЫДАЧА И ОЧИСТКА ===
        // =========================================================================
        
        // Очищаем все слоты микроволновки (если инвентарь модифицируемый)
        if (modifiableHandler != null) {
            for (int slotId = 1; slotId <= 12; slotId++) {
                modifiableHandler.setStackInSlot(slotId, ItemStack.EMPTY);
            }
        }
        
        if (world instanceof Level _level) {
            // 1. Выплёвываем готовую скрафченную еду
            for (Map.Entry<Item, Integer> entry : spawnItems.entrySet()) {
                int count = entry.getValue();
                int maxStack = entry.getKey().getDefaultMaxStackSize();
                while (count > 0) {
                    int stackSize = Math.min(count, maxStack);
                    ItemStack stack = new ItemStack(entry.getKey(), stackSize);
                    ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.6), (z + 0.5), stack);
                    _level.addFreshEntity(entityToSpawn);
                    count -= stackSize;
                }
            }

            // 2. Выплёвываем обратно все оставшиеся предметы И пустые емкости из-под жидкостей (сохраняя их измененный NBT!)
            for (ItemStack stack : microwaveContents) {
                if (!stack.isEmpty() && stack.getCount() > 0) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.6), (z + 0.5), stack);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }
    }

    // Вспомогательный метод для безопасного уменьшения количества обычных предметов в списке
    private static void decreaseItemCountInList(List<ItemStack> list, Item item, int countToRemove) {
        for (ItemStack stack : list) {
            if (stack.getItem() == item) {
                if (stack.getCount() >= countToRemove) {
                    stack.shrink(countToRemove);
                    break;
                } else {
                    countToRemove -= stack.getCount();
                    stack.setCount(0);
                }
            }
        }
    }

    private static Property<?> getPropertyByName(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null)
            return blockEntity.getPersistentData().getDoubleOr(tag, 0);
        return -1;
    }
}
