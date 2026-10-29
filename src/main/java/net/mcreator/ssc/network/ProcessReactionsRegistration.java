package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.chemistry.network.ProcessReactionsMessage;

/** @deprecated Use the canonical chemistry network registration. */
@Deprecated(forRemoval = false)
public final class ProcessReactionsRegistration {
    private ProcessReactionsRegistration() {
    }

    public static void handle(ProcessReactionsMessage message, IPayloadContext context) {
        net.ssc14.content.item.reagent.network.ProcessReactionsRegistration.handle(message, context);
    }
}
