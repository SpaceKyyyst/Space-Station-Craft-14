
package net.ssc14.content.menu.commerce;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.commerce.SalePriceRegistry;
import java.util.List;

public final class SaleRevenueService {
	private static final int SEARCH_RADIUS = 16;

	private SaleRevenueService() { }

	public static int total(LevelAccessor world, BlockPos consolePos) {
		if (world == null) return 0;

		int totalRevenue = 0;

		// Быстрый математический скан на наличие SALE_PALLET в радиусе 16 блоков
		for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
			for (int dy = -SEARCH_RADIUS; dy <= SEARCH_RADIUS; dy++) {
				for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
					BlockPos checkPos = consolePos.offset(dx, dy, dz);
					
					if (world.getBlockState(checkPos).is(Ssc14Blocks.SALE_PALLET.get())) {
						// Нашли продающий поддон! Сканируем зону ровно над ним
						BlockPos airPos = checkPos.above();
						AABB area = new AABB(airPos.getX(), airPos.getY() - 1, airPos.getZ(), airPos.getX() + 1, airPos.getY() + 1, airPos.getZ() + 1);
						List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, area);

						for (ItemEntity itemEntity : items) {
							ItemStack stack = itemEntity.getItem();
							// Умножаем цену за 1 шт. на количество предметов в стаке
							totalRevenue += SalePriceRegistry.price(stack) * stack.getCount();
						}
					}
				}
			}
		}

		return totalRevenue;
	}
}
