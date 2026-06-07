
package net.mcreator.ssc.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.world.inventory.Slot;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(value = Dist.CLIENT)
public class Interaction_PRProcedure {

    @SubscribeEvent
    public static void onKeyPress(ScreenEvent.KeyPressed.Pre event) {
        KeyMapping keybind = net.mcreator.ssc.init.Ssc14ModKeyMappings.INTERACTION;
        
        if (event.getKeyCode() != keybind.getKey().getValue()) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen == null) return;
        
        if (mc.screen instanceof AbstractContainerScreen<?> containerScreen) {
            // ✅ Переводим пиксели окна в координаты GUI (делим на масштаб)
            double scale = mc.getWindow().getGuiScale();
            double guiMouseX = mc.mouseHandler.xpos() / scale;
            double guiMouseY = mc.mouseHandler.ypos() / scale;
            
            for (Slot slot : containerScreen.getMenu().slots) {
                // Координаты слота в GUI-пространстве
                double slotX = slot.x + containerScreen.getGuiLeft();
                double slotY = slot.y + containerScreen.getGuiTop();
                
                // Проверяем попадание в квадрат 16x16
                if (guiMouseX >= slotX && guiMouseX < slotX + 16 &&
                    guiMouseY >= slotY && guiMouseY < slotY + 16) {

                    // ✅ FIX: Получаем порядковый номер слота в текущем меню
                    int slotIndex = containerScreen.getMenu().slots.indexOf(slot);
                    
                    if (slotIndex != -1) {
                        // Отправляем индекс слота вместо ID предмета
                        mc.player.connection.send(new ServerboundChatCommandPacket("ssc14_interact " + slotIndex));
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
