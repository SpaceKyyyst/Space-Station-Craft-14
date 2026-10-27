package net.ssc14.client.medical;

import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.medical.network.MedicalPresentationMessage;

/** Applies validated clientbound medical presentation snapshots. */
public final class MedicalPresentationHandler {
    private MedicalPresentationHandler() { }

    public static void handle(MedicalPresentationMessage message, IPayloadContext context) {
        if (context.flow() != PacketFlow.CLIENTBOUND) return;
        var receiver = context.player();
        if (!receiver.level().isClientSide()) return;
        var entity = receiver.getId() == message.entityId()
                ? receiver : receiver.level().getEntity(message.entityId());
        if (entity instanceof Player player
                && message.matches(player.getId(), player.getUUID(), player.level().dimension().identifier())) {
            MedicalClientPresentation.receive(player, message.active());
        }
    }
}
