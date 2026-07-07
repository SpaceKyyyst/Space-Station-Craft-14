
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

public class AirlockUpPlug_ClickProcedure {

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide()) return;
		
		// Позиция основного шлюза (на 1 блок ниже заглушки)
		BlockPos airlockPos = BlockPos.containing(x, y - 1, z);
		BlockState airlockState = world.getBlockState(airlockPos);
		
		// Проверяем, что блок соответствует тегу #ssc14:airlocks
		if (airlockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("ssc14:airlocks")))) {
			// Передаём управление основной процедуре
			// 🔧 Важно: приводим LevelAccessor к Level для sendBlockUpdated
			if (world instanceof Level level) {
				BaseAirlockOpenCloseProcedure.execute(
					level,
					airlockPos.getX() + 0.5, 
					airlockPos.getY() + 0.5, 
					airlockPos.getZ() + 0.5,
					airlockState,
					entity
				);
			}
		}
	}
}
