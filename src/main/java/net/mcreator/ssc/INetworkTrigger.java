
package net.mcreator.ssc;

import java.util.List;

public interface INetworkTrigger {
    // Возвращает список ID триггеров, которые поддерживает этот блок (например, "on", "off", "pulse")
    List<String> getAvailableTriggers();
    
    // Возвращает человекочитаемое имя для GUI по ID триггера
    String getTriggerName(String triggerId);
}
