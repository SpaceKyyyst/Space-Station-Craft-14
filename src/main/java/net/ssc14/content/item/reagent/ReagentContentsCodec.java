package net.ssc14.content.item.reagent;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;

/** Canonical codec for the persisted reagent contents component. */
public final class ReagentContentsCodec {
    public static final String NBT_KEY = "ssc_14_reagents";

    private ReagentContentsCodec() { }

    public static Map<String, Integer> read(CompoundTag root) {
        if (!root.contains(NBT_KEY)) {
            return Collections.emptyMap();
        }
        CompoundTag contents = root.getCompound(NBT_KEY).orElse(new CompoundTag());
        Map<String, Integer> amounts = new LinkedHashMap<>();
        for (String key : contents.keySet()) {
            int amount = contents.getInt(key).orElse(0);
            if (amount > 0 && !key.isBlank()) {
                amounts.put(key, amount);
            }
        }
        return Collections.unmodifiableMap(amounts);
    }

    public static CompoundTag write(Map<String, Integer> amounts) {
        CompoundTag contents = new CompoundTag();
        amounts.forEach(contents::putInt);
        return contents;
    }
}
