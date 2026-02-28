package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GravGenGUIpercentTextProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		return Math.round(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") / 20) + " %";
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}