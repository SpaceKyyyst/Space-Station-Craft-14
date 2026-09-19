
package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.mcreator.ssc.init.Ssc14ModItems;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MicrwGUIbutEProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide()) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        if (!(world instanceof Level level)) return;
        
        // ИСПРАВЛЕНО: Официальный метод NeoForge 26.1.2 для получения IItemHandler через новые капабилити
        var newHandler = level.getCapability(Capabilities.Item.BLOCK, pos, null);
        IItemHandler handler = newHandler == null ? null : IItemHandler.of(newHandler);
        if (handler == null) return;
        
        IItemHandlerModifiable modifiableHandler = null;
        if (handler instanceof IItemHandlerModifiable) {
            modifiableHandler = (IItemHandlerModifiable) handler;
        }
        if (modifiableHandler == null) return;

        ItemStack slot0 = modifiableHandler.getStackInSlot(0);
        ItemStack slot1 = modifiableHandler.getStackInSlot(1);
        ItemStack slot2 = modifiableHandler.getStackInSlot(2);
        ItemStack slot3 = modifiableHandler.getStackInSlot(3);

        // Объявляем и заполняем список содержимого на основе слотов инвентаря
        List<ItemStack> microwaveContents = new ArrayList<>();
        if (!slot0.isEmpty()) microwaveContents.add(slot0);
        if (!slot1.isEmpty()) microwaveContents.add(slot1);
        if (!slot2.isEmpty()) microwaveContents.add(slot2);
        if (!slot3.isEmpty()) microwaveContents.add(slot3);

        // В качестве заглушки берем время из NBT печки (или ставим 10 сек по умолчанию)
        int timer = 10; 

        checkRecipes(level, pos, modifiableHandler, microwaveContents, timer);
    }
    private static void checkRecipes(Level level, BlockPos pos, IItemHandlerModifiable inv, List<ItemStack> microwaveContents, int timer) {
        Map<Item, Integer> spawnItems = new HashMap<>();

        // РЕЦЕПТ №1: Соль 5u + Картофель -> [ 15 сек ] -> Космический фри
        if (timer == 15) {
            int potatoCount = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() == Ssc14ModItems.POTATO.get()) {
                    potatoCount += stack.getCount();
                }
            }
            
            int totalSaltAvailable = 0;
            for (ItemStack stack : microwaveContents) {
                if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                    totalSaltAvailable += net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("table_salt", 0);
                }
            }
            
            int possibleCraftsBySalt = totalSaltAvailable / 5;
            int canCraft = Math.min(potatoCount, possibleCraftsBySalt);
            
            if (canCraft > 0) {
                int potatoToRemove = canCraft;
                for (ItemStack stack : microwaveContents) {
                    if (stack.getItem() == Ssc14ModItems.POTATO.get()) {
                        if (stack.getCount() == potatoToRemove || stack.getCount() > potatoToRemove) {
                            stack.shrink(potatoToRemove);
                            break;
                        } else {
                            potatoToRemove -= stack.getCount();
                            stack.setCount(0);
                        }
                    }
                }
                
                int saltToRemove = canCraft * 5;
                for (ItemStack stack : microwaveContents) {
                    if (stack.getItem() instanceof net.mcreator.ssc.item.ReagentContainerItem) {
                        int saltInContainer = net.mcreator.ssc.ModReagents.getReagents(stack).getOrDefault("table_salt", 0);
                        if (saltInContainer > 0) {
                            int toTake = Math.min(saltToRemove, saltInContainer);
                            net.mcreator.ssc.ModReagents.removeReagent(stack, "table_salt", toTake);
                            saltToRemove -= toTake;
                            if (saltToRemove == 0) break;
                        }
                    }
                }
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
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.BUN.get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.CHEESE.get(), canCraft);
                decreaseItemCountInList(microwaveContents, Ssc14ModItems.MEAT.get(), canCraft);
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

        // Выдаем приготовленные предметы в выходной слот микроволновки (слот №4)
        if (!spawnItems.isEmpty()) {
            for (Map.Entry<Item, Integer> entry : spawnItems.entrySet()) {
                spawnOutput(inv, new ItemStack(entry.getKey(), entry.getValue()), 4);
            }
            playCookingSound(level, pos);
        }
    }

    private static void decreaseItemCountInList(List<ItemStack> list, Item item, int amount) {
        int toRemove = amount;
        for (ItemStack stack : list) {
            if (stack.getItem() == item) {
                if (stack.getCount() >= toRemove) {
                    stack.shrink(toRemove);
                    break;
                } else {
                    toRemove -= stack.getCount();
                    stack.setCount(0);
                }
            }
        }
    }

    private static void shrinkSlot(IItemHandlerModifiable inv, int slotId, int amount) {
        ItemStack stack = inv.getStackInSlot(slotId);
        if (stack.isEmpty()) return;
        if (stack.getCount() <= amount) {
            inv.setStackInSlot(slotId, ItemStack.EMPTY);
        } else {
            stack.shrink(amount);
            inv.setStackInSlot(slotId, stack);
        }
    }

    private static void spawnOutput(IItemHandlerModifiable inv, ItemStack result, int outputSlot) {
        ItemStack existingOutput = inv.getStackInSlot(outputSlot);
        if (existingOutput.isEmpty()) {
            inv.setStackInSlot(outputSlot, result);
        } else if (ItemStack.isSameItemSameComponents(existingOutput, result)) {
            int newCount = Math.min(existingOutput.getMaxStackSize(), existingOutput.getCount() + result.getCount());
            existingOutput.setCount(newCount);
            inv.setStackInSlot(outputSlot, existingOutput);
        }
    }

    private static void playCookingSound(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        var soundHolder = BuiltInRegistries.SOUND_EVENT.get(Identifier.fromNamespaceAndPath("ssc_14", "machine_microwave_done"));
        if (soundHolder != null && soundHolder.isPresent()) {
            level.playSound(null, pos, soundHolder.get().value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static void playFailSound(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        var soundHolder = BuiltInRegistries.SOUND_EVENT.get(Identifier.fromNamespaceAndPath("ssc_14", "machine_microwave_fail"));
        if (soundHolder != null && soundHolder.isPresent()) {
            level.playSound(null, pos, soundHolder.get().value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
