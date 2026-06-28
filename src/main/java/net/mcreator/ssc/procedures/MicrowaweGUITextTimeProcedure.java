package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class MicrowaweGUITextTimeProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		if (0 != getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer") && 0 == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer_2")) {
			return Math.round(Math.ceil(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer"))) + " \u0441";
		} else if (0 != getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer_2")) {
			return Math.round(Math.ceil(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "timer_2"))) + " \u0441";
		}
		return "";
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}