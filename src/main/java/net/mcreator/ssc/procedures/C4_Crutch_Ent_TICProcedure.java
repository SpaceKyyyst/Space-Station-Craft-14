package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.mcreator.ssc.entity.C4CrutchEntEntity;
import net.mcreator.ssc.Ssc14Mod;

public class C4_Crutch_Ent_TICProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Ssc14Mod.queueServerWork(2, () -> {
			if (entity instanceof C4CrutchEntEntity) {
				if (!entity.level().isClientSide())
					entity.discard();
			}
		});
	}
}