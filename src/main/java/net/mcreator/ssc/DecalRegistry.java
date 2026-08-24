
package net.mcreator.ssc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.ArrayList;

@EventBusSubscriber(modid = "ssc_14")
public class DecalRegistry {

    public record DecalListContainer(List<DecalData> decals) {
        public static final MapCodec<DecalListContainer> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DecalData.CODEC.listOf().fieldOf("decals").forGetter(DecalListContainer::decals)
        ).apply(instance, DecalListContainer::new));
    }

    public static AttachmentType<DecalListContainer> DECAL_ATTACHMENT;

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(NeoForgeRegistries.Keys.ATTACHMENT_TYPES)) {
            DECAL_ATTACHMENT = AttachmentType.<DecalListContainer>builder(() -> new DecalListContainer(new ArrayList<>()))
                .serialize(DecalListContainer.MAP_CODEC)
                .build();

            event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
                ResourceLocation.fromNamespaceAndPath("ssc_14", "chunk_decals"),
                () -> DECAL_ATTACHMENT
            );
        }
    }

    public static void addDecal(LevelChunk chunk, DecalData decal) {
        if (DECAL_ATTACHMENT == null) return;
        DecalListContainer container = chunk.getData(DECAL_ATTACHMENT);

        List<DecalData> decals = new ArrayList<>(container.decals());
        decals.removeIf(d -> d.pos().equals(decal.pos()) && d.face() == decal.face());
        decals.add(decal);

        chunk.setData(DECAL_ATTACHMENT, new DecalListContainer(decals));

        DecalNetwork.syncChunkToTrackingPlayers(chunk);
    }

    public static void removeDecalAt(LevelChunk chunk, BlockPos pos, Direction face) {
	    if (DECAL_ATTACHMENT == null) return;
	    DecalListContainer container = chunk.getData(DECAL_ATTACHMENT);
	    List<DecalData> decals = new ArrayList<>(container.decals());
	    
	    // Удаляем декаль, если совпадает и позиция, и сторона блока
	    boolean removed = decals.removeIf(d -> d.pos().equals(pos) && d.face() == face);
	    
	    if (removed) {
	        chunk.setData(DECAL_ATTACHMENT, new DecalListContainer(decals));
	        DecalNetwork.syncChunkToTrackingPlayers(chunk);
	    }
	}

    public static void removeDecalsInRadius(LevelChunk chunk, BlockPos center, double radius) {
        if (chunk.getLevel() instanceof ServerLevel serverLevel) {
            removeDecalsInRadius(serverLevel, center, radius);
        } else {
            removeDecalsInRadiusFromChunk(chunk, center, radius);
        }
    }

    public static void removeDecalsInRadius(ServerLevel level, BlockPos center, double radius) {
        if (DECAL_ATTACHMENT == null) return;
        int r = (int) Math.ceil(radius);
        int minChunkX = (center.getX() - r) >> 4;
        int maxChunkX = (center.getX() + r) >> 4;
        int minChunkZ = (center.getZ() - r) >> 4;
        int maxChunkZ = (center.getZ() + r) >> 4;

        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                ChunkAccess access = level.getChunk(cx, cz, ChunkStatus.FULL, false);
                if (access instanceof LevelChunk chunk) {
                    removeDecalsInRadiusFromChunk(chunk, center, radius);
                }
            }
        }
    }

    public static void removeDecalsInRadiusFromChunk(LevelChunk chunk, BlockPos center, double radius) {
        if (DECAL_ATTACHMENT == null) return;
        DecalListContainer container = chunk.getData(DECAL_ATTACHMENT);

        List<DecalData> decals = new ArrayList<>(container.decals());
        boolean removed = decals.removeIf(d -> d.pos().distSqr(center) <= radius * radius);
        if (removed) {
            chunk.setData(DECAL_ATTACHMENT, new DecalListContainer(decals));
            DecalNetwork.syncChunkToTrackingPlayers(chunk);
        }
    }
}
