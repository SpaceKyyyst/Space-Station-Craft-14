
package net.mcreator.ssc.procedures;

// === ИМПОРТЫ NEOFORGE 1.21.8 ===
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
// ScreenEvent в NeoForge находится здесь:
import net.neoforged.neoforge.client.event.ScreenEvent;

// === ИМПОРТЫ MINECRAFT ===
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

// === ИМПОРТ ТВОЕГО МОДА ===
import net.mcreator.ssc.Ssc14Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * CuriosGuiModifierProcedure
 * Скрывает лишние виджеты Curios API в интерфейсе SSC14
 * Совместимо с NeoForge 1.21.8 + Java 21
 */
@EventBusSubscriber(
    modid = Ssc14Mod.MODID,   // ID твоего мода
    value = Dist.CLIENT       // Только клиентская сторона
    // ❌ Параметр "bus" убран — в твоей версии он не поддерживается
)
public class CuriosGuiModifierProcedure {

    /**
     * Обработчик события ПОСЛЕ инициализации экрана
     * @param event событие ScreenEvent.Init.Post
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onScreenInitPost(ScreenEvent.Init.Post event) {
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return; // Защита от null

        // === ОПРЕДЕЛЕНИЕ ТИПА ЭКРАНА ===
        
        // Экран креатива — обрабатываем, но не кликаем
        if (event.getScreen() instanceof CreativeModeInventoryScreen) {
            handleWidgets(event, false);
            return;
        }

        // Обычный инвентарь — кликаем только в выживании
        if (event.getScreen() instanceof InventoryScreen) {
            handleWidgets(event, !mc.player.isCreative());
            
        // Экран Curios (определяем по имени класса)
        } else if (event.getScreen().getClass().getName().contains("Curios")) {
            handleWidgets(event, false);
        }
    }

    /**
     * Поиск и скрытие маленьких виджетов Curios
     * @param event событие инициализации
     * @param shouldClick нажимать ли на кнопки автоматически
     */
    private static void handleWidgets(ScreenEvent.Init.Post event, boolean shouldClick) {
        
        // Список виджетов для удаления (чтобы не менять коллекцию во время перебора)
        List<AbstractWidget> toRemove = new ArrayList<>();
        
        // 🔧 getListenersList() возвращает List<Object> — проверяем тип вручную
        for (Object listener : event.getListenersList()) {
            if (listener instanceof AbstractWidget widget) {
                
                String className = widget.getClass().getName().toLowerCase();
                
                // Критерии: имя содержит "curios" И ширина <= 24 пикселя
                if (className.contains("curios") && widget.getWidth() <= 24) {
                    
                    // Авто-клик для совместимости с Curios API
                    if (shouldClick && widget instanceof AbstractButton button) {
                        button.onPress();
                    }
                    
                    toRemove.add(widget);
                }
            }
        }

        // === СКРЫТИЕ И УДАЛЕНИЕ ===
        for (AbstractWidget widget : toRemove) {
            // 🔧 В Minecraft 1.21.8 поля visible/active — ПУБЛИЧНЫЕ, используем прямое обращение:
            widget.visible = false;      // Скрыть
            widget.active = false;       // Деактивировать
            widget.setPosition(-10000, -10000); // Убрать за экран (доп. защита)
            
            // Удалить из обработки события
            event.removeListener(widget);
        }
    }
}
