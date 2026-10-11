
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.mcreator.ssc.SaleRegistry;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import java.util.List;

public class SaleGUITotalRevenueProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		if (world == null) return "$:  0";

		BlockPos consolePos = BlockPos.containing(x, y, z);
		int totalRevenue = 0;
		int r = 16; // Наш фиксированный радиус поиска поддонов

		// Быстрый математический скан на наличие SALE_PALLET в радиусе 16 блоков
		for (int dx = -r; dx <= r; dx++) {
			for (int dy = -r; dy <= r; dy++) {
				for (int dz = -r; dz <= r; dz++) {
					BlockPos checkPos = consolePos.offset(dx, dy, dz);
					
					if (world.getBlockState(checkPos).is(Ssc14ModBlocks.SALE_PALLET.get())) {
						// Нашли продающий поддон! Сканируем зону ровно над ним
						BlockPos airPos = checkPos.above();
						AABB area = new AABB(airPos.getX(), airPos.getY() - 1, airPos.getZ(), airPos.getX() + 1, airPos.getY() + 1, airPos.getZ() + 1);
						List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, area);

						for (ItemEntity itemEntity : items) {
							ItemStack stack = itemEntity.getItem();
							// Умножаем цену за 1 шт. на количество предметов в стаке
							totalRevenue += SaleRegistry.getPrice(stack) * stack.getCount();
						}
					}
				}
			}
		}

		return "$:  " + totalRevenue;
	}
}
