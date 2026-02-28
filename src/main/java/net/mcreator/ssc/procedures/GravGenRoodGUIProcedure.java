package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GravGenRoodGUIProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (true == ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("gravity") instanceof BooleanProperty _getbp1 && (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getbp1))) {
			return true;
		}
		return false;
	}
}