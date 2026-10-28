package net.ssc14.content.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/** Keeps spawned item entities from expiring on the server. */
@EventBusSubscriber
public final class ItemLifetimeHandler {
    private ItemLifetimeHandler() {
    }

    @SubscribeEvent
    public static void onItemSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity().level().isClientSide()
                || !(event.getEntity() instanceof ItemEntity item)) {
            return;
        }
        item.lifespan = Integer.MAX_VALUE;
    }
}
