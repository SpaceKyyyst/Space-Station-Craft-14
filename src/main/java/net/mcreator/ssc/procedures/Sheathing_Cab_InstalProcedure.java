
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.block.SheathingBlock;
import net.mcreator.ssc.EnergyNetworkManager;

public class Sheathing_Cab_InstalProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState oldBlockstate, Entity entity) {
		if (entity == null || world == null)
			return;

		// 1. Проверяем направление клика (игрок должен кликнуть по верхней грани)
		Direction hitDirection = entity.level().clip(new ClipContext(
				entity.getEyePosition(1f), 
				entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)), 
				ClipContext.Block.OUTLINE, 
				ClipContext.Fluid.NONE, 
				entity
		)).getDirection();

		if (hitDirection != Direction.UP) {
			return;
		}

		BlockPos pos = BlockPos.containing(x, y, z);
		BlockState currentState = world.getBlockState(pos);

		// 2. Убеждаемся, что мы кликаем именно по блоку обшивки
		if (currentState.getBlock() != Ssc14ModBlocks.SHEATHING.get()) {
			return;
		}

		// Получаем предмет в руке игрока
		ItemStack mainHandItem = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
		if (mainHandItem.isEmpty()) {
			return;
		}

		BooleanProperty targetProperty = null;

		// 3. Компактное определение целевого свойства по предмету в руке
		if (mainHandItem.getItem() == Ssc14ModItems.LOW_VOLTAGE_CABLE.get()) {
			targetProperty = SheathingBlock.LV;
		} else if (mainHandItem.getItem() == Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get()) {
			targetProperty = SheathingBlock.MV;
		} else if (mainHandItem.getItem() == Ssc14ModItems.HIGH_VOLTAGE_CABLE.get()) {
			targetProperty = SheathingBlock.HV;
		}

		// 4. Если в руке кабель, и на обшивке его ЕЩЁ НЕТ — устанавливаем!
		if (targetProperty != null && currentState.hasProperty(targetProperty)) {
			boolean alreadyHasCable = currentState.getValue(targetProperty);
			
			if (!alreadyHasCable) {
				// Устанавливаем обновленный блокстейт с кабелем в мир
				BlockState updatedState = currentState.setValue(targetProperty, true);
				world.setBlock(pos, updatedState, 3);
				
				// Забираем 1 кабель из руки игрока
				mainHandItem.shrink(1);

				// ЖЕЛЕЗНЫЙ ТРИГГЕР: Принудительно заставляем систему проснуться и пересобрать энергосеть в этой точке!
				if (!world.isClientSide()) {
					EnergyNetworkManager.updatePosition(world, pos);
				}
			}
		}
	}
}
