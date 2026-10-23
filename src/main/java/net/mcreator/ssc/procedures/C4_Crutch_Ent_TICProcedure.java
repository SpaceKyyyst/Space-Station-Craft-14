package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.ssc14.content.entity.C4CrutchEntEntity;
import net.mcreator.ssc.Ssc14Mod;

public class C4_Crutch_Ent_TICProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel serverOwner)) return;
		if (entity == null)
			return;
		Ssc14Mod.queueServerWork(serverOwner, 2, () -> {
			if (entity instanceof C4CrutchEntEntity) {
				if (!entity.level().isClientSide())
					entity.discard();
			}
		}).orThrow();
	}
}
