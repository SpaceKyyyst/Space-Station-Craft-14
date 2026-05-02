package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.entity.ChairEntityEntity;

import java.util.Comparator;

public class OficeChair_AutorotateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		// Защита от выполнения на клиенте
		if (world.isClientSide())
			return;
		// === ШАГ 1: Поиск игрока (точно как в оригинале) ===
		// Сначала быстрая проверка на наличие любого игрока в малом радиусе
		Vec3 playerCheckCenter = new Vec3(x + 0.5, y + 0.5, z + 0.5);
		boolean playerNearby = !world.getEntitiesOfClass(Player.class, new AABB(Vec3.ZERO, Vec3.ZERO).move(playerCheckCenter).inflate(0.15 / 2d), e -> true).isEmpty();
		if (!playerNearby)
			return;
		// Затем точный поиск ближайшего игрока в радиусе 0.2
		Player procPlayer = world.getEntitiesOfClass(Player.class, AABB.ofSize(playerCheckCenter, 0.2, 0.2, 0.2), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(playerCheckCenter))).findFirst().orElse(null);
		if (procPlayer == null || !procPlayer.isPassenger() || !(procPlayer.getRootVehicle() instanceof ChairEntityEntity))
			return;
		// === ШАГ 2: Поиск сущности-стула (ВАЖНО: y, а не y+0.5!) ===
		Vec3 chairCheckCenter = new Vec3(x + 0.5, y, z + 0.5); // ← Ключевое отличие!
		ChairEntityEntity procChair = world.getEntitiesOfClass(ChairEntityEntity.class, AABB.ofSize(chairCheckCenter, 0.1, 0.1, 0.1), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(chairCheckCenter))).findFirst()
				.orElse(null);
		if (procChair == null)
			return;
		// === ШАГ 3: Определение целевого поворота (точные условия оригинала) ===
		float yRot = procPlayer.getYRot();
		int targetYRot;
		Direction targetDir;
		// Точные условия из оригинала (включая границы!)
		if (yRot > -45f && yRot < 45f) {
			targetYRot = 0;
			targetDir = Direction.SOUTH;
		} else if (yRot >= 45f && yRot < 135f) {
			targetYRot = 90;
			targetDir = Direction.WEST;
		} else if (yRot <= -45f && yRot > -135f) {
			targetYRot = -90;
			targetDir = Direction.EAST;
		} else { // (yRot >= 135f || yRot <= -135f)
			targetYRot = 180;
			targetDir = Direction.NORTH;
		}
		// === ШАГ 4: Применение вращения к сущности ===
		// Обновляем только если разница значима (защита от дребезга)
		if (Math.abs(procChair.getYRot() - targetYRot) > 0.1f) {
			procChair.setYRot(targetYRot);
			procChair.setXRot(0f);
			procChair.setYBodyRot(targetYRot);
			procChair.setYHeadRot(targetYRot);
			procChair.yRotO = targetYRot;
			procChair.xRotO = 0f;
			if (procChair instanceof LivingEntity le) {
				le.yBodyRotO = targetYRot;
				le.yHeadRotO = targetYRot;
			}
		}
		// === ШАГ 5: Обновление блока (facing/axis) ===
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockState bs = world.getBlockState(pos);
		boolean updated = false;
		// Попытка обновить "facing"
		Property<?> prop = bs.getBlock().getStateDefinition().getProperty("facing");
		if (prop instanceof EnumProperty<?> ep && ep.getPossibleValues().contains(targetDir)) {
			@SuppressWarnings("unchecked")
			EnumProperty<Direction> facingProp = (EnumProperty<Direction>) ep;
			if (bs.getValue(facingProp) != targetDir) {
				world.setBlock(pos, bs.setValue(facingProp, targetDir), 3);
				updated = true;
			}
		}
		// Если не вышло — пробуем "axis"
		if (!updated) {
			prop = bs.getBlock().getStateDefinition().getProperty("axis");
			if (prop instanceof EnumProperty<?> ep && ep.getPossibleValues().contains(targetDir.getAxis())) {
				@SuppressWarnings("unchecked")
				EnumProperty<Direction.Axis> axisProp = (EnumProperty<Direction.Axis>) ep;
				if (bs.getValue(axisProp) != targetDir.getAxis()) {
					world.setBlock(pos, bs.setValue(axisProp, targetDir.getAxis()), 3);
				}
			}
		}
		if (Ssc14ModBlocks.VOIDB.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ASH, x, y, z, 0, 0, 0, 0, 0);
		}
	}
}