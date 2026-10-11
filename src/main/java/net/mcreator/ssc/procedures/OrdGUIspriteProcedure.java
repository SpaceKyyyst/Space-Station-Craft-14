package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class OrdGUIspriteProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		double Tab_i = 0;
		return getBlockNBTNumber(world, BlockPos.containing(x, y, z), "TAB") - 1;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}
}