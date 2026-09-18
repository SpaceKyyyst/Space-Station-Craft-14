
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
import net.neoforged.neoforge.items.IItemHandlerModifiable; // Берем актуальный интерфейс модовых инвентарей NeoForge
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier; // ИСПРАВЛЕНО: победный Identifier

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(modid = "ssc_14")
public class EquipmentAutoEquipmentProcedure {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        // Замена метода получения активной руки на безопасную из самого события ПКМ
        if (event.getHand() == null) {
            return;
        }
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (!(entity instanceof Player player)) {
            return;
        }

        // Берем руку из события, если оно передано, иначе дефолтную основную
        InteractionHand hand = (event instanceof PlayerInteractEvent.RightClickItem rce) ? rce.getHand() : InteractionHand.MAIN_HAND;
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.isEmpty()) {
            return;
        }

        // Проверяем Curios-теги через Identifier
        if (top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(player).isPresent() && !itemInHand.is(ItemTags.create(Identifier.parse("ssc14:noautoequip")))) {
            
            Optional<ICuriosItemHandler> handlerOpt = CuriosApi.getCuriosInventory(player);
            if (handlerOpt.isEmpty()) {
                return;
            }

            ICuriosItemHandler handler = handlerOpt.get();
            String targetSlot = null;
            IItemHandlerModifiable targetInv = null;

            for (Map.Entry<String, ICurioStacksHandler> entry : handler.getCurios().entrySet()) {
                String slotId = entry.getKey();
                ICurioStacksHandler stacksHandler = entry.getValue();
                
                // Получаем модифицируемый обработчик предметов NeoForge
                IItemHandlerModifiable inv = stacksHandler.getStacks();
                if (inv == null) continue;
                
                if (inv.isItemValid(0, itemInHand)) {
                    ItemStack stackInSlot = inv.getStackInSlot(0);

                    if (stackInSlot.isEmpty()) {
                        targetSlot = slotId;
                        targetInv = inv;
                        break; 
                    } else if (targetSlot == null) {
                        if ("headdress".equals(slotId) && stackInSlot.is(ItemTags.create(Identifier.parse("ssc14:hardsuits_helmets")))) {
                            continue; 
                        }
                        targetSlot = slotId;
                        targetInv = inv;
                    }
                }
            }

            if (targetSlot != null && targetInv != null) {
                if (event instanceof PlayerInteractEvent.RightClickItem rightClickEvent) {
                    rightClickEvent.setCanceled(true);
                    rightClickEvent.setCancellationResult(InteractionResult.SUCCESS);
                }

                if (player.level().isClientSide()) {
                    return; 
                }

                ItemStack equippedItem = targetInv.getStackInSlot(0);

                if (equippedItem.isEmpty()) {
                    // АДАПТАЦИЯ: используем безопасный метод insertItem под требования NeoForge 26
                    targetInv.insertItem(0, itemInHand.copyWithCount(1), false);
                    itemInHand.shrink(1);
                } else {
                    if ("headdress".equals(targetSlot) && equippedItem.is(ItemTags.create(Identifier.parse("ssc14:hardsuits_helmets")))) {
                        return; 
                    }

                    // АДАПТАЦИЯ: Безопасный обмен предметов через ванильные стандарты извлечения NeoForge
                    ItemStack extracted = targetInv.extractItem(0, 1, false);
                    targetInv.insertItem(0, itemInHand.copyWithCount(1), false);
                    itemInHand.shrink(1);
                    player.setItemInHand(hand, extracted);
                }
            }
        }
    }
}
