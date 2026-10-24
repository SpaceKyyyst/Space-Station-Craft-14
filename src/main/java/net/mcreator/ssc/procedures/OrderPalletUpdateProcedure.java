
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.commerce.OrderPalletRegistryService;

public class OrderPalletUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		OrderPalletRegistryService.register(world, BlockPos.containing(x, y, z));
	}
}
