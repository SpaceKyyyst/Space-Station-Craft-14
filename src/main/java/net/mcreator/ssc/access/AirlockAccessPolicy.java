package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/** Any configured department access grants entry; an unrestricted door admits everyone. */
public final class AirlockAccessPolicy {
    private AirlockAccessPolicy() { }

    public static boolean permits(CompoundTag doorData, Entity entity, boolean emergency) {
        // Mirrors SS14 AccessReader.Enabled and deny tags: a disabled reader is open,
        // while deny entries always win over a matching allow entry.
        if (!doorData.getBooleanOr("access_enabled", true) || emergency) return true;
        long required = 0;
        for (int index = 0; index < IdCardRegistry.KEYS.size(); index++) {
            if (doorData.getBooleanOr(IdCardRegistry.KEYS.get(index), false)) required |= 1L << index;
        }
        if (required == 0) return true;
        if (!(entity instanceof Player player)) return false;
        long permissions = PlayerAccessService.currentPermissions(player);
        long denied = doorData.getLongOr("access_deny_mask", 0L);
        return (permissions & denied) == 0 && (permissions & required) != 0;
    }
}
