
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NetworkManager {

    // Вызывается из процедуры твоего блока-триггера (например, кнопки)
    public static void trigger(Level world, BlockPos sourcePos, String triggerId) {
        NetworkSavedData data = NetworkSavedData.get(world);
        for (NetworkSavedData.NetworkConnection conn : data.connections) {
            if (conn.sourcePos.equals(sourcePos) && conn.triggerId.equals(triggerId)) {
                executeAction(world, conn.targetPos, conn.actionId);
            }
        }
    }

    private static void executeAction(Level world, BlockPos targetPos, String actionId) {
        BlockState state = world.getBlockState(targetPos);
        // Проверяем сам блок
        if (state.getBlock() instanceof INetworkAction actionBlock) {
            actionBlock.executeNetworkAction(actionId, world, targetPos);
            return;
        }
        // Проверяем BlockEntity (если логика внутри него)
        BlockEntity be = world.getBlockEntity(targetPos);
        if (be instanceof INetworkAction actionBe) {
            actionBe.executeNetworkAction(actionId, world, targetPos);
        }
    }
}
