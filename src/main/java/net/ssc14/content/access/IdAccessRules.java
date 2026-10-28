package net.ssc14.content.access;

import java.util.List;
import net.minecraft.nbt.CompoundTag;

/** Pure access-card rules shared by menu adapters and assignment services. */
public final class IdAccessRules {
    private IdAccessRules() { }

    public static List<String> grantedAccessKeys(CompoundTag cardData) {
        return IdJobCatalog.accessKeys().stream()
                .filter(key -> cardData.getBoolean(key).orElse(false))
                .toList();
    }
}
