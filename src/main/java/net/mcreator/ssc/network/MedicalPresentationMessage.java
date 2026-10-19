package net.mcreator.ssc.network;

import java.util.UUID;
import net.mcreator.ssc.medical.MedicalClientPresentation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** A bounded clientbound snapshot, addressed to one player incarnation in one dimension. */
public record MedicalPresentationMessage(int entityId, UUID playerId, Identifier dimension, boolean active)
        implements CustomPacketPayload {
    private static final int MAX_DIMENSION_LENGTH = 256;
    public static final Type<MedicalPresentationMessage> TYPE = new Type<>(Identifier.parse("ssc_14:medical_presentation"));
    public static final StreamCodec<FriendlyByteBuf, MedicalPresentationMessage> CODEC = StreamCodec.of((buffer, message) -> {
        buffer.writeVarInt(message.entityId());
        buffer.writeUUID(message.playerId());
        buffer.writeUtf(message.dimension().toString(), MAX_DIMENSION_LENGTH);
        buffer.writeBoolean(message.active());
    }, buffer -> new MedicalPresentationMessage(buffer.readVarInt(), buffer.readUUID(),
            Identifier.parse(buffer.readUtf(MAX_DIMENSION_LENGTH)), buffer.readBoolean()));

    @Override public Type<MedicalPresentationMessage> type() { return TYPE; }

    public boolean matches(int id, UUID uuid, Identifier level) {
        return entityId == id && playerId.equals(uuid) && dimension.equals(level);
    }

    public static void handle(MedicalPresentationMessage message, IPayloadContext context) {
        // PayloadRegistrar's default MAIN handler owns dispatch; never accept client medical decisions.
        if (context.flow() != PacketFlow.CLIENTBOUND) return;
        var receiver = context.player();
        if (!receiver.level().isClientSide()) return;
        var entity = receiver.getId() == message.entityId() ? receiver : receiver.level().getEntity(message.entityId());
        if (entity instanceof Player player && message.matches(player.getId(), player.getUUID(), player.level().dimension().identifier())) {
            MedicalClientPresentation.receive(player, message.active());
        }
    }
}
