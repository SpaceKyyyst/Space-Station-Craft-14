
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import java.util.List;

public class NetworkManager {

    // Вызывается из процедуры твоего блока-триггера (например, кнопки)
    public static void trigger(Level world, BlockPos sourcePos, String triggerId) {
        if (!(world instanceof ServerLevel) || !world.hasChunkAt(sourcePos)) {
            return;
        }
        NetworkSavedData data = NetworkSavedData.get(world);
        // An action may edit connections; dispatch this signal against a stable snapshot.
        for (NetworkSavedData.NetworkConnection conn : List.copyOf(data.connections)) {
            if (conn.sourcePos.equals(sourcePos) && conn.triggerId.equals(triggerId)) {
                executeAction(world, conn.targetPos, conn.actionId);
            }
        }
    }

    private static void executeAction(Level world, BlockPos targetPos, String actionId) {
        if (!world.hasChunkAt(targetPos)) {
            return;
        }
        BlockState state = world.getBlockState(targetPos);
        // Проверяем сам блок
        if (state.getBlock() instanceof INetworkAction actionBlock && actionBlock.getAvailableActions().contains(actionId)) {
            actionBlock.executeNetworkAction(actionId, world, targetPos);
            return;
        }
        // Проверяем BlockEntity (если логика внутри него)
        BlockEntity be = world.getBlockEntity(targetPos);
        if (be instanceof INetworkAction actionBe && actionBe.getAvailableActions().contains(actionId)) {
            actionBe.executeNetworkAction(actionId, world, targetPos);
        }
    }
}
