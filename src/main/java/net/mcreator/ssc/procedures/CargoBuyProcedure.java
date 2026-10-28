
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import net.ssc14.content.commerce.cargo.CargoCatalog;
import net.mcreator.ssc.network.Ssc14ModVariables;
import net.ssc14.content.block.Ssc14Blocks;

public class CargoBuyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Player player, int slotId) {
		if (player == null || world.isClientSide()) return;

		BlockPos consolePos = BlockPos.containing(x, y, z);
		BlockEntity be = world.getBlockEntity(consolePos);
		int currentTab = be != null ? (int) be.getPersistentData().getDoubleOr("TAB", 0) : 0;

		CargoCatalog.CargoItem cargoItem = CargoCatalog.getItemAt(currentTab, slotId);
		if (cargoItem == null) return;

		// 1. Поиск ближайшего физического поддона в радиусе 32 блоков
		BlockPos nearestPallet = null;
		double shortestDistance = Double.MAX_VALUE;
		int r = 32;

		for (int dx = -r; dx <= r; dx++) {
			for (int dy = -r; dy <= r; dy++) {
				for (int dz = -r; dz <= r; dz++) {
					BlockPos checkPos = consolePos.offset(dx, dy, dz);
					if (world.getBlockState(checkPos).is(Ssc14Blocks.ORDER_PALLET.get())) {
						double dist = checkPos.distSqr(consolePos);
						if (dist < shortestDistance) {
							shortestDistance = dist;
							nearestPallet = checkPos;
						}
					}
				}
			}
		}

		// Если поддон не найден
		if (nearestPallet == null) {
			player.sendSystemMessage(Component.literal("§cОшибка: Поддон заказа (ORDER_PALLET) не найден в радиусе 32 блоков от консоли!"));
			playCargoSound(world, x, y, z, "ssc_14:cargo_buy_error");
			return;
		}

		int price = cargoItem.getPrice();
		double currentBudget = Ssc14ModVariables.WorldVariables.get(world).Station_Budget;

		// Проверка бюджета
		if (currentBudget < price) {
			player.sendSystemMessage(Component.literal("§cОшибка: Недостаточно средств на бюджете станции!"));
			playCargoSound(world, x, y, z, "ssc_14:cargo_buy_error");
			return;
		}

		// 2. Списываем средства и вызываем каноничную синхронизацию MCreator
		Ssc14ModVariables.WorldVariables.get(world).Station_Budget = currentBudget - price;
		Ssc14ModVariables.WorldVariables.get(world).markSyncDirty();

		BlockPos spawnPos = nearestPallet.above();

		// Логика спавна предметов с гашением инерции
		if (world instanceof ServerLevel serverLevel) {
			int stacksToSpawn = cargoItem.getCount();
			for (int s = 0; s != stacksToSpawn; s++) {
				ItemStack spawnStack = cargoItem.getStack();
				ItemEntity itemEntity = new ItemEntity(serverLevel, spawnPos.getX() + 0.5, spawnPos.getY() + 0.2, spawnPos.getZ() + 0.5, spawnStack);
				itemEntity.setNoPickUpDelay();
				itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().x * 0.1, itemEntity.getDeltaMovement().y, itemEntity.getDeltaMovement().z * 0.1);
				serverLevel.addFreshEntity(itemEntity);
			}
		}

		playCargoSound(world, x, y, z, "ssc_14:cargo_buy_success");
		String itemName = cargoItem.getStack().getHoverName().getString();
		player.sendSystemMessage(Component.literal("§aЗаказ [" + itemName + "] успешно доставлен на поддон."));
	}

	private static void playCargoSound(LevelAccessor world, double x, double y, double z, String soundPath) {
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse(soundPath)), SoundSource.BLOCKS, 1.0F, 1.0F);
			} else {
				_level.playLocalSound((x + 0.5), (y + 0.5), (z + 0.5), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse(soundPath)), SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
		}
	}
}
