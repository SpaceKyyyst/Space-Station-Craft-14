
package net.mcreator.ssc.procedures;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

// ДОБАВЛЕНЫ ИМПОРТЫ ДЛЯ ТЕГОВ И РЕСУРСОВ
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.ssc.Ssc14Mod;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(modid = "ssc_14")
public class EquipmentAutoEquipmentProcedure {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        // Проверяем, что сущность - это игрок
        if (!(entity instanceof Player player)) {
            return;
        }

        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.isEmpty()) {
            return;
        }

        // Проверяем, является ли предмет курьо И не входит ли он в тег "noautoequip"
        // Мы используем уже готовую переменную itemInHand, что делает код чище и быстрее
        if (Ssc14Mod.CuriosApiHelper.isCurioItem(itemInHand) && !itemInHand.is(ItemTags.create(ResourceLocation.parse("ssc14:noautoequip")))) {
            
            // Получаем инвентарь Curios игрока
            Optional<ICuriosItemHandler> handlerOpt = CuriosApi.getCuriosInventory(player);
            if (handlerOpt.isEmpty()) {
                return;
            }

            ICuriosItemHandler handler = handlerOpt.get();
            
            String targetSlot = null;
            IItemHandlerModifiable targetInv = null;

            // Перебираем все доступные слоты игрока напрямую из его обработчика
            for (Map.Entry<String, ICurioStacksHandler> entry : handler.getCurios().entrySet()) {
                String slotId = entry.getKey();
                ICurioStacksHandler stacksHandler = entry.getValue();
                
                // Получаем стандартный обработчик предметов NeoForge для этого слота
                IItemHandlerModifiable inv = stacksHandler.getStacks();
                
                // ПРОВЕРКА: спрашиваем у самого инвентаря, можно ли положить сюда этот предмет
                if (inv.isItemValid(0, itemInHand)) {
                    if (inv.getStackInSlot(0).isEmpty()) {
                        targetSlot = slotId;
                        targetInv = inv;
                        break; // Приоритет 1: нашли пустой подходящий слот, сразу используем его
                    } else if (targetSlot == null) {
                        // Приоритет 2: запоминаем первый подходящий слот на случай, если все они заняты (для обмена)
                        targetSlot = slotId;
                        targetInv = inv;
                    }
                }
            }

            // Если мы нашли куда надевать (пустой или занятый, но подходящий слот)
            if (targetSlot != null && targetInv != null) {
                // Отменяем событие, чтобы предотвратить анимацию взмаха рукой и стандартное действие ПКМ
                if (event instanceof PlayerInteractEvent.RightClickItem rightClickEvent) {
                    rightClickEvent.setCanceled(true);
                    rightClickEvent.setCancellationResult(InteractionResult.SUCCESS);
                }

                // Вся модификация инвентаря должна происходить ТОЛЬКО на сервере!
                if (player.level().isClientSide()) {
                    return; 
                }

                ItemStack equippedItem = targetInv.getStackInSlot(0);

                if (equippedItem.isEmpty()) {
                    // Слот пустой: просто надеваем предмет
                    targetInv.insertItem(0, itemInHand.copyWithCount(1), false);
                    itemInHand.shrink(1);
                } else {
                    // Слот занят: выполняем обмен (swap)
                    // 1. Забираем предмет из слота
                    ItemStack extracted = targetInv.extractItem(0, 1, false);
                    // 2. Кладем в слот предмет из руки
                    targetInv.insertItem(0, itemInHand.copyWithCount(1), false);
                    // 3. Уменьшаем стак в руке
                    itemInHand.shrink(1);
                    // 4. Кладем старый предмет в руку игрока
                    player.setItemInHand(hand, extracted);
                }
            }
        }
    }
}
