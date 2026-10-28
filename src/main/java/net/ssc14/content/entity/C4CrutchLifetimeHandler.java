package net.ssc14.content.entity;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

public final class C4CrutchLifetimeHandler {
	public static void execute(LevelAccessor world, Entity entity) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel serverOwner)) return;
		if (entity == null)
			return;
		ServerWorkScheduler.schedule(serverOwner, 2, () -> {
			if (entity instanceof C4CrutchEntEntity) {
				if (!entity.level().isClientSide())
					entity.discard();
			}
		});
	}
}
