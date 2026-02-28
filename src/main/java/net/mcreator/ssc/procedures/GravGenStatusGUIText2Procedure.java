package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GravGenStatusGUIText2Procedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (2000 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && 0 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && true == getBlockNBTLogic(world, BlockPos.containing(x, y, z), "active")) {
			return true;
		}
		return false;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}