
package net.mcreator.ssc;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = "ssc_14")
public class DecalNetwork {
    public static final Map<ChunkPos, List<DecalData>> CLIENT_DECAL_STORAGE = new ConcurrentHashMap<>();

    public record ClientboundChunkDecalsPacket(ChunkPos chunkPos, List<DecalData> decals) implements CustomPacketPayload {
        public static final Type<ClientboundChunkDecalsPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("ssc_14", "chunk_decals"));

        @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundChunkDecalsPacket> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public ClientboundChunkDecalsPacket decode(RegistryFriendlyByteBuf buffer) {
                ChunkPos pos = new ChunkPos(buffer.readLong());
                int size = buffer.readVarInt();
                List<DecalData> list = new ArrayList<>(size);
                for (int i = 0; i != size; i++) {
                    list.add(DecalData.STREAM_CODEC.decode(buffer));
                }
                return new ClientboundChunkDecalsPacket(pos, list);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, ClientboundChunkDecalsPacket msg) {
                buffer.writeLong(msg.chunkPos.toLong());
                buffer.writeVarInt(msg.decals.size());
                for (DecalData decal : msg.decals) {
                    DecalData.STREAM_CODEC.encode(buffer, decal);
                }
            }
        };
    }

    @SubscribeEvent
    public static void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("ssc_14").versioned("1");
        registrar.playToClient(
            ClientboundChunkDecalsPacket.TYPE,
            ClientboundChunkDecalsPacket.STREAM_CODEC,
            (packet, context) -> context.enqueueWork(() -> {
                if (packet.decals().isEmpty()) {
                    CLIENT_DECAL_STORAGE.remove(packet.chunkPos());
                } else {
                    CLIENT_DECAL_STORAGE.put(packet.chunkPos(), packet.decals());
                }
                ClientDecalRenderer.ensureRegistered();
            })
        );
    }

    public static void syncChunkToTrackingPlayers(LevelChunk chunk) {
        if (chunk.getLevel() instanceof ServerLevel serverLevel && DecalRegistry.DECAL_ATTACHMENT != null) {
            DecalRegistry.DecalListContainer container = chunk.getData(DecalRegistry.DECAL_ATTACHMENT);
            List<DecalData> decals = (container != null) ? container.decals() : List.of();
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, chunk.getPos(), new ClientboundChunkDecalsPacket(chunk.getPos(), decals));
        }
    }

    @EventBusSubscriber(modid = "ssc_14")
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onChunkWatch(ChunkWatchEvent.Watch event) {
            LevelChunk chunk = event.getChunk();
            if (DecalRegistry.DECAL_ATTACHMENT == null) return;
            DecalRegistry.DecalListContainer container = chunk.getData(DecalRegistry.DECAL_ATTACHMENT);
            if (container != null && !container.decals().isEmpty()) {
                PacketDistributor.sendToPlayer(event.getPlayer(), new ClientboundChunkDecalsPacket(chunk.getPos(), container.decals()));
            }
        }
    }
}
