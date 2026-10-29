package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;

/** @deprecated Use the canonical server/client menu state registrations. */
@Deprecated(forRemoval = false)
public final class MenuStateUpdateRegistration {
    private MenuStateUpdateRegistration() { }

    public static void handle(MenuStateUpdateMessage message, IPayloadContext context) {
        if (context.flow() == net.minecraft.network.protocol.PacketFlow.SERVERBOUND) {
            net.ssc14.integration.neoforge.menu.network.MenuStateServerRegistration.handle(message, context);
        } else {
            net.ssc14.client.menu.MenuStateClientRegistration.handle(message, context);
        }
    }
}
