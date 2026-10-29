package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.network.NetConfigGUISyncMessage;

/** @deprecated Use the canonical network configuration menu handler. */
@Deprecated(forRemoval = false)
public final class NetConfigGUISyncRegistration {
    private NetConfigGUISyncRegistration() {
    }

    public static void handle(NetConfigGUISyncMessage message, IPayloadContext context) {
        net.ssc14.content.menu.network.NetConfigGUISyncRegistration.handle(message, context);
    }
}
