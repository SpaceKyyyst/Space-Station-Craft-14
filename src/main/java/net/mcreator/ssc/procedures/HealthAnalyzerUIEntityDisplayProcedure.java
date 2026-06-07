
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class HealthAnalyzerUIEntityDisplayProcedure {

	public static Entity execute(Entity entity) {
		if (entity == null) return null;

		// Проверяем, на какую сущность смотрит игрок на дистанции до 5 блоков
		// pick(double reachDistance, float partialTicks, boolean hitFluids)
		HitResult hitResult = entity.pick(5.0D, 0.0F, false);
		if (hitResult.getType() == HitResult.Type.ENTITY) {
			return ((EntityHitResult) hitResult).getEntity();
		}

		// Если игрок смотрит вниз (pitch > 75), возвращаем самого игрока
		if (entity.getXRot() > 75.0F) {
			return entity;
		}

		return null;
	}
}
