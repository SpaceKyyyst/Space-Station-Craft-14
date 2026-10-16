package net.mcreator.ssc;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/** Persistent gas state; permeability and simulation queues are rebuilt at runtime. */
public final class AtmosphereSavedData extends SavedData {
    public static final Codec<AtmosphereSavedData> CODEC = CompoundTag.CODEC.xmap(
            AtmosphereSavedData::read, AtmosphereSavedData::write);
    public static final SavedDataType<AtmosphereSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("ssc_14", "atmosphere"), AtmosphereSavedData::new, CODEC);
    private final Map<Long, AtmosRegion> regions = new HashMap<>();

    public static AtmosphereSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    Map<Long, AtmosRegion> regions() { return regions; }

    private CompoundTag write() {
        var root = new CompoundTag();
        var entries = new ListTag();
        for (var region : regions.values()) {
            var tag = new CompoundTag();
            region.writeToNBT(tag);
            if (!tag.getList("cells").orElseThrow().isEmpty()) entries.add(tag);
        }
        root.put("regions", entries);
        return root;
    }

    private static AtmosphereSavedData read(CompoundTag root) {
        var data = new AtmosphereSavedData();
        var entries = root.getList("regions").orElse(new ListTag());
        for (int index = 0; index < entries.size(); index++) {
            var entry = entries.getCompound(index);
            if (entry.isEmpty()) continue;
            var tag = entry.get();
            if (tag.getInt("x").isEmpty() || tag.getInt("y").isEmpty() || tag.getInt("z").isEmpty()) continue;
            int x = tag.getInt("x").orElseThrow();
            int y = tag.getInt("y").orElseThrow();
            int z = tag.getInt("z").orElseThrow();
            var region = new AtmosRegion(x, y, z);
            region.readFromNBT(tag);
            data.regions.put(AtmosphereManager.packRegionKey(x, y, z), region);
        }
        return data;
    }
}
