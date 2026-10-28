package net.ssc14.common.storage;

import java.util.List;
import net.minecraft.nbt.CompoundTag;

/** Shared access-role contract for standard and secure closets. */
public final class ClosetAccessPolicy {
    private static final List<String> ROLES = List.of(
            "Technical", "Service", "Out", "gun_room", "HoS", "Brig", "Medical", "Crio",
            "Security", "Engineer", "Command", "Detective", "PNT", "Scientist",
            "Supply_Department", "Atmos", "Kitchen", "Uridic", "Gidroponic", "Teatre",
            "Bar", "Cleaner", "Utilizat", "Chemistry", "Church", "CE", "Qm", "CMO",
            "RD", "HoP", "Captain", "Blue_Sh");

    private ClosetAccessPolicy() { }

    public static boolean hasAccess(CompoundTag entityData, CompoundTag blockData) {
        if (entityData == null || blockData == null) return true;
        for (String role : ROLES) {
            if (blockData.getBoolean(role).orElse(false)
                    && !entityData.getBoolean(role).orElse(false)) return false;
        }
        return true;
    }
}
