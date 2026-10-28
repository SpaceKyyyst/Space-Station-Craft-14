package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.access.IdConsoleIntegrationService;

public class IDconsole_OPENMENUProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!world.isClientSide() && world instanceof net.minecraft.world.level.Level level) {
			IdConsoleIntegrationService.resetBuffer(level, BlockPos.containing(x, y, z));
		}
	}
}
