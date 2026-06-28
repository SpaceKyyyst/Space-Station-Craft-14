
package net.mcreator.ssc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.core.BlockPos;
import com.mojang.serialization.Codec;
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
            System.out.println("[SS14-Decals] Attachment Registered Successfully");
        }
    }

    public static void addDecal(LevelChunk chunk, DecalData decal) {
        if (DECAL_ATTACHMENT == null) return;
        DecalListContainer container = chunk.getData(DECAL_ATTACHMENT);
        
        // ХАК: Оборачиваем список в изменяемый ArrayList, защищаясь от ImmutableCollections в Java 21
        List<DecalData> decals = new ArrayList<>(container.decals());
        
        decals.removeIf(d -> d.pos().equals(decal.pos()) && d.face() == decal.face());
        decals.add(decal);
        
        // Записываем обновленный изменяемый список обратно в контейнер чанка
        chunk.setData(DECAL_ATTACHMENT, new DecalListContainer(decals));
        
        System.out.println("[SS14-Decals] Decal added securely. Current count in chunk: " + decals.size());
        
        if (chunk.getLevel() != null) {
            chunk.getLevel().blockEntityChanged(decal.pos());
        }
        DecalNetwork.syncChunkToTrackingPlayers(chunk);
    }

    public static void removeDecalsInRadius(LevelChunk chunk, BlockPos center, double radius) {
        if (DECAL_ATTACHMENT == null) return;
        DecalListContainer container = chunk.getData(DECAL_ATTACHMENT);
        
        // ХАК: Оборачиваем список в изменяемый ArrayList
        List<DecalData> decals = new ArrayList<>(container.decals());
        
        boolean removed = decals.removeIf(d -> d.pos().distSqr(center) <= radius * radius);
        if (removed) {
            chunk.setData(DECAL_ATTACHMENT, new DecalListContainer(decals));
            System.out.println("[SS14-Decals] Decals removed securely from chunk data");
            if (chunk.getLevel() != null) {
                chunk.getLevel().blockEntityChanged(center);
            }
            DecalNetwork.syncChunkToTrackingPlayers(chunk);
        }
    }
}
