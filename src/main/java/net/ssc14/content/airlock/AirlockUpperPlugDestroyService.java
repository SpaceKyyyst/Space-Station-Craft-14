package net.ssc14.content.airlock;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public final class AirlockUpperPlugDestroyService {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
	}
}
