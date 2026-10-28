package net.ssc14.integration.neoforge.player;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Clientbound snapshot of one entity's SSC14 player variables. */
public record Ssc14PlayerVariablesSyncPayload(Ssc14PlayerVariables data, int playerId)
        implements CustomPacketPayload {
    public static final Type<Ssc14PlayerVariablesSyncPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("ssc_14", "player_variables_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, Ssc14PlayerVariablesSyncPayload>
            STREAM_CODEC = StreamCodec.of(
                    Ssc14PlayerVariablesSyncPayload::encode,
                    Ssc14PlayerVariablesSyncPayload::decode);

    private static void encode(
            RegistryFriendlyByteBuf buffer, Ssc14PlayerVariablesSyncPayload message) {
        TagValueOutput output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING, buffer.registryAccess());
        message.data().serialize(output);
        buffer.writeInt(message.playerId());
        buffer.writeNbt(output.buildResult());
    }

    private static Ssc14PlayerVariablesSyncPayload decode(RegistryFriendlyByteBuf buffer) {
        Ssc14PlayerVariables data = new Ssc14PlayerVariables();
        int playerId = buffer.readInt();
        data.deserialize(TagValueInput.create(
                ProblemReporter.DISCARDING, buffer.registryAccess(), buffer.readNbt()));
        return new Ssc14PlayerVariablesSyncPayload(data, playerId);
    }

    @Override
    public Type<Ssc14PlayerVariablesSyncPayload> type() {
        return TYPE;
    }

    public static void handleData(
            Ssc14PlayerVariablesSyncPayload message, IPayloadContext context) {
        if (context.flow() != PacketFlow.CLIENTBOUND || message.data() == null) {
            return;
        }
        context.enqueueWork(() -> {
            Entity player = context.player().level().getEntity(message.playerId());
            if (player == null) {
                return;
            }
            TagValueOutput output = TagValueOutput.createWithContext(
                    ProblemReporter.DISCARDING, context.player().registryAccess());
            message.data().serialize(output);
            player.getData(Ssc14PlayerAttachments.PLAYER_VARIABLES).deserialize(
                    TagValueInput.create(
                            ProblemReporter.DISCARDING,
                            context.player().registryAccess(),
                            output.buildResult()));
        }).exceptionally(error -> {
            context.connection().disconnect(Component.literal(error.getMessage()));
            return null;
        });
    }
}
