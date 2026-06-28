
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;

import java.util.Optional;
import java.util.List;

public class Specify_CLICK_Procedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;

		double maxDistance = 15.0; // Дистанция клика
		Vec3 startPos = entity.getEyePosition(1.0f);
		Vec3 viewVec = entity.getViewVector(1.0f);
		Vec3 endPos = startPos.add(viewVec.scale(maxDistance));

		// 1. Трассировка по блокам
		BlockHitResult blockHit = entity.level().clip(new ClipContext(
			startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity
		));
		
		// Если блок найден, ограничиваем дистанцию поиска сущностей до него
		Vec3 finalHitVec = blockHit.getLocation();
		double currentMaxDistance = blockHit.getType() != HitResult.Type.MISS ? blockHit.getLocation().distanceTo(startPos) : maxDistance;

		// 2. Поиск пересечений с хитбоксами сущностей
		AABB searchBox = entity.getBoundingBox().expandTowards(viewVec.scale(currentMaxDistance)).inflate(1.0D);
		List<Entity> targets = entity.level().getEntities(entity, searchBox, 
			target -> !target.isSpectator() && target.isPickable()
		);

		double closestDistance = currentMaxDistance;
		boolean hitEntity = false;

		// Математически проверяем луч на пересечение с AABB каждого моба
		for (Entity target : targets) {
			AABB targetBB = target.getBoundingBox().inflate(target.getPickRadius());
			Optional<Vec3> hitOptional = targetBB.clip(startPos, endPos);
			
			if (hitOptional.isPresent()) {
				Vec3 hitVec = hitOptional.get();
				double distance = startPos.distanceTo(hitVec);
				
				// Если существо ближе, чем блок или предыдущее найденное существо
				if (distance < closestDistance) {
					closestDistance = distance;
					finalHitVec = hitVec;
					hitEntity = true;
				}
			}
		}

		// 3. Если попали в блок или сущность, рассчитываем смещение к игроку
		if (hitEntity || blockHit.getType() == HitResult.Type.BLOCK) {
			// Вычисляем вектор от точки попадания к глазам игрока
			Vec3 toPlayer = startPos.subtract(finalHitVec);
			
			// Нормализуем вектор (делаем длину равной 1)
			if (toPlayer.lengthSqr() > 1.0E-4D) {
				toPlayer = toPlayer.normalize();
			} else {
				toPlayer = new Vec3(0, 0, 0);
			}

			// Смещаем точку спавна на 2 пикселя (0.125 блока) по направлению к игроку
			double X = finalHitVec.x + (toPlayer.x * 0.125);
			double Y = finalHitVec.y + (toPlayer.y * 0.125) + 0.25;
			double Z = finalHitVec.z + (toPlayer.z * 0.125);

			if (world instanceof ServerLevel _level) {
				_level.sendParticles((SimpleParticleType) (Ssc14ModParticleTypes.SPECIFY_P.get()), X, Y, Z, 1, 0, 0, 0, 0);
			}
		}
	}
}
