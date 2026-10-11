
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.mcreator.ssc.network.Ssc14ModVariables;

public class OrderPalletUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world == null || world.isClientSide()) return;

		BlockPos pos = BlockPos.containing(x, y, z);
		String posStr = pos.getX() + "," + pos.getY() + "," + pos.getZ() + ";";

		// Извлекаем текущий текстовый реестр поддонов этого измерения
		String currentRegistry = Ssc14ModVariables.WorldVariables.get(world).PalletRegistry;
		
		// Если этого поддона еще нет в списке — дописываем его в вечный реестр мира
		if (!currentRegistry.contains(posStr)) {
			Ssc14ModVariables.WorldVariables.get(world).PalletRegistry = currentRegistry + posStr;
			Ssc14ModVariables.WorldVariables.get(world).setDirty();
		}
	}
}
