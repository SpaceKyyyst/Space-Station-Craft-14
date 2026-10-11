
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.mcreator.ssc.SaleRegistry;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import java.util.List;

public class Sale_GUI_buttonSALEProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Player player) {
		if (world == null || world.isClientSide() || player == null) return;

		BlockPos consolePos = BlockPos.containing(x, y, z);
		int totalEarned = 0;
		int r = 16;

		// Скан поддонов сбыта в радиусе 16 блоков
		for (int dx = -r; dx <= r; dx++) {
			for (int dy = -r; dy <= r; dy++) {
				for (int dz = -r; dz <= r; dz++) {
					BlockPos checkPos = consolePos.offset(dx, dy, dz);
					if (world.getBlockState(checkPos).is(Ssc14ModBlocks.SALE_PALLET.get())) {
						BlockPos airPos = checkPos.above();
						AABB area = new AABB(airPos.getX(), airPos.getY()-1, airPos.getZ(), airPos.getX() + 1, airPos.getY() + 1, airPos.getZ() + 1);
						List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, area);

						for (ItemEntity itemEntity : items) {
							ItemStack stack = itemEntity.getItem();
							totalEarned += SaleRegistry.getPrice(stack) * stack.getCount();

							if (world instanceof ServerLevel serverLevel) {
								serverLevel.sendParticles(ParticleTypes.POOF, itemEntity.getX(), itemEntity.getY() + 0.2, itemEntity.getZ(), 8, 0.1, 0.1, 0.1, 0.02);
							}
							itemEntity.discard();
						}
					}
				}
			}
		}

		if (totalEarned > 0) {
			// Начисляем кредиты и принудительно синхронизируем баланс через markSyncDirty()
			Ssc14ModVariables.WorldVariables.get(world).Station_Budget += totalEarned;
			Ssc14ModVariables.WorldVariables.get(world).markSyncDirty();
			
			player.sendSystemMessage(Component.literal("§aТовары со склада успешно сбыты! Получено: +" + totalEarned + " кредитов."));
		} else {
			player.sendSystemMessage(Component.literal("§cОшибка: В радиусе 16 блоков нет поддонов с подходящим товаром для продажи!"));
		}
	}
}
