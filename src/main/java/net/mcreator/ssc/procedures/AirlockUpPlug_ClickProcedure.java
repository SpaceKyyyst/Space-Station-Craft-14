package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class AirlockUpPlug_ClickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BaseAirlockOpenCloseProcedure.execute(world, x, y - 1, z, world.getBlockState(BlockPos.containing(x, y - 1, z)), entity);
	}
}