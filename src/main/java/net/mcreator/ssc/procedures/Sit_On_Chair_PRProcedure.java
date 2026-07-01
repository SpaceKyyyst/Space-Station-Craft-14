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

import java.util.Comparator;

public class Sit_On_Chair_PRProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = Ssc14ModEntities.CHAIR_ENTITY.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), EntitySpawnReason.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setDeltaMovement(0, 0, 0);
			}
		}
		if (!world.getEntitiesOfClass(ChairEntityEntity.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(new Vec3((x + 0.5), y, (z + 0.5))).inflate(0.1 / 2d), e -> true).isEmpty()) {
			entity.startRiding((findEntityInWorldRange(world, ChairEntityEntity.class, (x + 0.5), y, (z + 0.5), 0.1)));
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}