
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import java.util.List;

public interface INetworkAction {
    // Возвращает список ID действий (например, "open", "close", "toggle")
    List<String> getAvailableActions();
    
    // Возвращает человекочитаемое имя для GUI по ID действия
    String getActionName(String actionId);
    
    // Вызывается, когда сеть требует выполнить это действие
    void executeNetworkAction(String actionId, Level world, BlockPos pos);
}
