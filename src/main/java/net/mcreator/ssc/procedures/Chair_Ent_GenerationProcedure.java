package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModEntities;
import net.mcreator.ssc.entity.ChairEntityEntity;

public class Chair_Ent_GenerationProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!(!world.getEntitiesOfClass(ChairEntityEntity.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), y, (z + 0.5))).inflate(0.1 / 2d), e -> true).isEmpty())) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = Ssc14ModEntities.CHAIR_ENTITY.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), EntitySpawnReason.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setDeltaMovement(0, 0, 0);
				}
			}
		}
	}
}