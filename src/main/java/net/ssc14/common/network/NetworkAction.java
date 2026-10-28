package net.ssc14.common.network;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/** A server-addressable action exposed by a block or block entity. */
public interface NetworkAction {
    List<String> getAvailableActions();

    String getActionName(String actionId);

    void executeNetworkAction(String actionId, Level level, BlockPos pos);
}
