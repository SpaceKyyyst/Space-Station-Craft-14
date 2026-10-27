
package net.mcreator.ssc;

import net.ssc14.integration.neoforge.decal.DecalData;
import net.ssc14.integration.neoforge.decal.Ssc14DecalAttachments;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.mcreator.ssc.network.validation.ChunkPacketCoordinates;
import net.ssc14.core.network.PayloadLimits;

@EventBusSubscriber(modid = "ssc_14")
public class DecalNetwork {
    public static final Map<ChunkPos, List<DecalData>> CLIENT_DECAL_STORAGE = new ConcurrentHashMap<>();

    public record ClientboundChunkDecalsPacket(ChunkPos chunkPos, List<DecalData> decals) implements CustomPacketPayload {
        // Even minimal decal entries consume at least 15 bytes of the payload.
        private static final int MIN_DECAL_BYTES = 15;

        public ClientboundChunkDecalsPacket {
            decals = List.copyOf(decals);
        }
        public static final Type<ClientboundChunkDecalsPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("ssc_14", "chunk_decals"));

        @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundChunkDecalsPacket> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public ClientboundChunkDecalsPacket decode(RegistryFriendlyByteBuf buffer) {
                long packedPos = buffer.readLong();
                ChunkPos pos = new ChunkPos(ChunkPacketCoordinates.x(packedPos), ChunkPacketCoordinates.z(packedPos));
                
                int size = PayloadLimits.requireCount(buffer.readVarInt(), buffer.readableBytes() / MIN_DECAL_BYTES);
                List<DecalData> list = new ArrayList<>(size);
                for (int i = 0; i != size; i++) {
                    list.add(DecalData.STREAM_CODEC.decode(buffer));
                }
                return new ClientboundChunkDecalsPacket(pos, list);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, ClientboundChunkDecalsPacket msg) {
                // ИСПРАВЛЕНО: Чистая математика сборки long чанка через открытые геттеры x() и z() (работает на любой версии Minecraft без ошибок доступа!)
                long packedChunkPos = ChunkPacketCoordinates.pack(msg.chunkPos.x(), msg.chunkPos.z());
                buffer.writeLong(packedChunkPos);
                
                buffer.writeVarInt(msg.decals.size());
                for (DecalData decal : msg.decals) {
                    DecalData.STREAM_CODEC.encode(buffer, decal);
                }
            }
        };
    }

    @SubscribeEvent
    public static void registerPackets(RegisterPayloadHandlersEvent event) {
        String version = "1";
        CompatibilityPayloadRegistrar registrar = new CompatibilityPayloadRegistrar(
                event.registrar("ssc_14").versioned(version), version);
        registrar.playToClient(
            ClientboundChunkDecalsPacket.TYPE,
            ClientboundChunkDecalsPacket.STREAM_CODEC,
            (packet, context) -> context.enqueueWork(() -> {
                if (packet.decals().isEmpty()) {
                    CLIENT_DECAL_STORAGE.remove(packet.chunkPos());
                } else {
                    CLIENT_DECAL_STORAGE.put(packet.chunkPos(), packet.decals());
                }
                // ИСПРАВЛЕНО: Правильный вызов рендерера из основного пакета net.mcreator.ssc
                ClientDecalRenderer.ensureRegistered();
            })
        );
    }

    public static void syncChunkToTrackingPlayers(LevelChunk chunk) {
        if (chunk.getLevel() instanceof ServerLevel serverLevel) {
            List<DecalData> decals = Ssc14DecalAttachments.decals(chunk);
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, chunk.getPos(), new ClientboundChunkDecalsPacket(chunk.getPos(), decals));
        }
    }

    @EventBusSubscriber(modid = "ssc_14")
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onChunkUnload(ChunkEvent.Unload event) {
            if (event.getLevel().isClientSide()) {
                CLIENT_DECAL_STORAGE.remove(event.getChunk().getPos());
            }
        }

        @SubscribeEvent
        public static void onLevelUnload(LevelEvent.Unload event) {
            if (event.getLevel().isClientSide()) {
                CLIENT_DECAL_STORAGE.clear();
            }
        }

        @SubscribeEvent
        public static void onChunkWatch(ChunkWatchEvent.Watch event) {
            LevelChunk chunk = event.getChunk();
            List<DecalData> decals = Ssc14DecalAttachments.decals(chunk);
            if (!decals.isEmpty()) {
                PacketDistributor.sendToPlayer(event.getPlayer(), new ClientboundChunkDecalsPacket(chunk.getPos(), decals));
            }
        }
    }
}
