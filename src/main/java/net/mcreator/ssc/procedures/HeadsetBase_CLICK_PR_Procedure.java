package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.item.communication.HeadsetKeyService;

/** @deprecated Use {@link HeadsetKeyService}. */
@Deprecated(forRemoval = false)
public class HeadsetBase_CLICK_PR_Procedure {
    public static void execute(LevelAccessor world, Entity entity) {
        HeadsetKeyService.interact(world, entity);
    }
}
