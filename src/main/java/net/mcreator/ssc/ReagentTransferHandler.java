
package net.mcreator.ssc;

import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = "ssc_14", value = Dist.CLIENT)
public class ReagentTransferHandler {

    @SubscribeEvent
    public static void onScreenMouseClick(ScreenEvent.MouseButtonPressed.Pre event) {
        // Проверяем строго нажатие СКМ (ID = 2)
        if (event.getButton() != 2) {
            return;
        }

        if (!(event.getScreen() instanceof AbstractContainerScreen<?> containerScreen)) {
            return;
        }

        // Находим слот под курсором мыши напрямую через калькулятор экрана
        Slot slot = containerScreen.getSlotUnderMouse();
        if (slot == null) {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ItemStack targetStack = slot.getItem(); // Предмет в инвентаре, по которому кликнули
        ItemStack mainHandStack = player.getMainHandItem(); // Предмет в хотбаре (в руках персонажа)

        // Проверяем, что оба предмета — химические контейнеры
        if (!(targetStack.getItem() instanceof ReagentContainerItem) || !(mainHandStack.getItem() instanceof ReagentContainerItem)) {
            return;
        }

        // Если в источнике (в хотбаре) нет реагентов, переливать нечего
        if (ModReagents.getReagents(mainHandStack).isEmpty()) {
            return; 
        }

        // Отправляем сетевой пакет на сервер
        if (Minecraft.getInstance().getConnection() != null) {
            System.out.println("[SSC-CLIENT] Клик СКМ прошел проверки. Отправка пакета для слота: " + slot.index);
            Minecraft.getInstance().getConnection().send(
                new ProcessReactionsMessage(slot.index, ItemStack.EMPTY, "hotbar_transfer")
            );
        }

        // Отменяем ванильный клик, чтобы креатив-режим не дублировал предметы
        event.setCanceled(true);
    }
}
