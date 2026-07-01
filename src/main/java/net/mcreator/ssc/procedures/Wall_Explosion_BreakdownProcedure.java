
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class Wall_Explosion_BreakdownProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Explosion explosion) {
		if (world == null || explosion == null) return;

		BlockPos pos = BlockPos.containing(x, y, z);

		// 1. Получаем центр взрыва
		double expX = explosion.center().x;
		double expY = explosion.center().y;
		double expZ = explosion.center().z;

		// 2. Получаем базовую мощность (радиус) взрыва (у ТНТ это 4.0, у C4 может быть больше)
		float radius = explosion.radius();

		// 3. Считаем точное расстояние от центра взрыва до этого блока стены
		double distance = Math.sqrt(pos.distToCenterSqr(expX, expY, expZ));

		// --- НАСТРОЙКА ЗОН РАЗРУШЕНИЯ ---
		// Полное уничтожение, если блок ближе, чем 50% от максимального радиуса взрыва
		double fullDestructionZone = radius * 0.5; 

		if (distance <= fullDestructionZone) {
			// Близко к центру: полностью уничтожаем стену и дропаем ресурсы
			Block.dropResources(world.getBlockState(pos), world, pos, null);
			world.destroyBlock(pos, false);
		} else {
			// На границе взрыва: превращаем в каркас с сохранением свойств (направление и т.д.)
			BlockState carcaseState = Ssc14ModBlocks.WALL_CARCASE.get().defaultBlockState();
			BlockState oldState = world.getBlockState(pos);
			
			for (Property<?> oldProperty : oldState.getProperties()) {
				Property carcaseProperty = carcaseState.getBlock().getStateDefinition().getProperty(oldProperty.getName());
				if (carcaseProperty != null && carcaseState.getValue(carcaseProperty) != null) {
					try {
						carcaseState = carcaseState.setValue(carcaseProperty, oldState.getValue(oldProperty));
					} catch (Exception e) {
						// Игнорируем несовпадающие свойства
					}
				}
			}
			// Заменяем блок на каркас
			world.setBlock(pos, carcaseState, 3);
		}
	}
}
