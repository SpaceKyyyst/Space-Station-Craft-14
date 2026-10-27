package net.ssc14.integration.neoforge.decal;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Owns persisted decal collections attached to chunks. */
@EventBusSubscriber(modid = "ssc_14")
public final class Ssc14DecalAttachments {
    private static AttachmentType<DecalList> type;

    private Ssc14DecalAttachments() { }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        if (!event.getRegistryKey().equals(NeoForgeRegistries.Keys.ATTACHMENT_TYPES)) {
            return;
        }
        type = AttachmentType.<DecalList>builder(() -> new DecalList(List.of()))
                .serialize(DecalList.MAP_CODEC)
                .build();
        event.register(
                NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
                Identifier.fromNamespaceAndPath("ssc_14", "chunk_decals"),
                () -> type);
    }

    public static List<DecalData> decals(LevelChunk chunk) {
        return type == null ? List.of() : chunk.getData(type).decals();
    }

    public static void add(LevelChunk chunk, DecalData decal) {
        if (type == null) {
            return;
        }
        List<DecalData> updated = new ArrayList<>(decals(chunk));
        updated.removeIf(existing -> existing.pos().equals(decal.pos())
                && existing.face() == decal.face());
        updated.add(decal);
        chunk.setData(type, new DecalList(List.copyOf(updated)));
    }

    public static boolean remove(LevelChunk chunk, BlockPos pos, Direction face) {
        if (type == null) {
            return false;
        }
        List<DecalData> updated = new ArrayList<>(decals(chunk));
        boolean removed = updated.removeIf(decal -> decal.pos().equals(pos)
                && decal.face() == face);
        if (removed) {
            chunk.setData(type, new DecalList(List.copyOf(updated)));
        }
        return removed;
    }

    public static boolean removeInRadius(LevelChunk chunk, BlockPos center, double radius) {
        if (type == null) {
            return false;
        }
        double squaredRadius = radius * radius;
        List<DecalData> updated = new ArrayList<>(decals(chunk));
        boolean removed = updated.removeIf(decal -> decal.pos().distSqr(center) <= squaredRadius);
        if (removed) {
            chunk.setData(type, new DecalList(List.copyOf(updated)));
        }
        return removed;
    }

    private record DecalList(List<DecalData> decals) {
        private static final MapCodec<DecalList> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(DecalData.CODEC.listOf().fieldOf("decals").forGetter(DecalList::decals))
                        .apply(instance, DecalList::new));
    }
}
