package net.mcreator.ssc;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/** Lazily restores sections, preserving the existing region/cell NBT representation. */
public final class AtmosphereSavedData extends SavedData {
    private static final SavedDataType<AtmosphereSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("ssc_14", "atmosphere"), AtmosphereSavedData::new,
            CompoundTag.CODEC.xmap(AtmosphereSavedData::decode, AtmosphereSavedData::encode));
    private final Map<Long, CompoundTag> storedRegions = new HashMap<>();
    private final Map<Long, AtmosRegion> liveRegions = new HashMap<>();

    static AtmosphereSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    CompoundTag restore(long key) { return storedRegions.remove(key); }
    void track(long key, AtmosRegion region) { liveRegions.put(key, region); }
    Iterable<Long> savedRegionKeys() { return java.util.List.copyOf(storedRegions.keySet()); }

    static AtmosphereSavedData decode(CompoundTag tag) {
        AtmosphereSavedData data = new AtmosphereSavedData();
        ListTag regions = tag.getList("regions").orElseGet(ListTag::new);
        for (int i = 0; i < regions.size(); i++) {
            CompoundTag region = regions.getCompound(i).orElse(null);
            if (region == null || region.getInt("x").isEmpty() || region.getInt("y").isEmpty()
                    || region.getInt("z").isEmpty()) continue;
            long key = AtmosphereManager.packRegionKey(region.getInt("x").get(),
                    region.getInt("y").get(), region.getInt("z").get());
            data.storedRegions.put(key, region.copy());
        }
        return data;
    }

    CompoundTag encode() {
        CompoundTag tag = new CompoundTag();
        ListTag regions = new ListTag();
        storedRegions.values().forEach(region -> regions.add(region.copy()));
        for (AtmosRegion region : liveRegions.values()) {
            CompoundTag saved = new CompoundTag();
            region.writeToNBT(saved);
            if (!saved.getList("cells").orElseGet(ListTag::new).isEmpty()) regions.add(saved);
        }
        tag.put("regions", regions);
        return tag;
    }
}
