package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GravGenGUIETAtextProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		String bufer_text = "";
		if (1400 <= getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && 2000 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			bufer_text = "1:" + Math.round(Math.abs(60 - getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") / 20));
		} else if (1200 <= getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && 1400 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			bufer_text = "1:0" + Math.round(Math.abs(60 - getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") / 20));
		} else if (200 <= getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && 1200 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			bufer_text = "0:" + Math.round(Math.abs(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") / 20));
		} else if (0 < getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") && 200 > getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			bufer_text = "0:0" + Math.round(Math.abs(getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") / 20));
		} else if (0 == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy") || 2000 == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "b_energy")) {
			bufer_text = "N/D";
		}
		return bufer_text;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}