package net.ssc14.content.block.airlock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.ssc14.content.access.IdCardRegistry;
import net.ssc14.content.access.PlayerAccessService;

/** Any configured department access grants entry; an unrestricted door admits everyone. */
public final class AirlockAccessPolicyService {
    private AirlockAccessPolicyService() { }

    public static boolean permits(CompoundTag doorData, Entity entity, boolean emergency) {
        if (emergency) return true;
        long required = 0;
        for (int index = 0; index < IdCardRegistry.KEYS.size(); index++) {
            if (doorData.getBooleanOr(IdCardRegistry.KEYS.get(index), false)) required |= 1L << index;
        }
        if (required == 0) return true;
        return entity instanceof Player player
                && (PlayerAccessService.currentPermissions(player) & required) != 0;
    }
}
