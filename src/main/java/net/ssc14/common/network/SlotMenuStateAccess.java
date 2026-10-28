package net.ssc14.common.network;

import java.util.Map;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.ssc14.core.network.MenuStateAccess;

/** Menu state contract for legacy procedures that inspect custom slots. */
public interface SlotMenuStateAccess extends MenuStateAccess {
    Map<Integer, Slot> getSlots();

    default void sendMenuStateUpdate(Player player, int elementType, String name,
            Object value, boolean needClientUpdate) {
        MenuStateTransportPort.send(this, player, elementType, name, value);
    }
}
