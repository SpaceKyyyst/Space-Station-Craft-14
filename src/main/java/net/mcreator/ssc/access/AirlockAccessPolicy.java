package net.mcreator.ssc.access;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.ssc14.content.block.airlock.AirlockAccessPolicyService;

/** Compatibility facade for canonical airlock access policy evaluation. */
@Deprecated(forRemoval = false)
public final class AirlockAccessPolicy {
    private AirlockAccessPolicy() {
    }

    public static boolean permits(CompoundTag doorData, Entity entity, boolean emergency) {
        return AirlockAccessPolicyService.permits(doorData, entity, emergency);
    }
}
