package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class SteelTableStateUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tables"))) || !(world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:tables")))) {
			// Обновляем 5 блоков: центр + 4 направления
			int centerX = (int) Math.floor(x);
			int centerY = (int) Math.floor(y);
			int centerZ = (int) Math.floor(z);
			// Список координат для обновления: [x, z]
			int[][] offsets = {{0, 0}, // центр
					{0, -1}, // север
					{1, 0}, // восток
					{0, 1}, // юг
					{-1, 0} // запад
			};
			for (int[] offset : offsets) {
				int posX = centerX + offset[0];
				int posY = centerY;
				int posZ = centerZ + offset[1];
				BlockPos pos = new BlockPos(posX, posY, posZ);
				BlockState blockState = world.getBlockState(pos);
				// Пропускаем, если это не стол
				if (!blockState.is(BlockTags.create(ResourceLocation.parse("ssc14:tables")))) {
					continue;
				}
				// Проверяем соседей ВОКРУГ этого блока (встроенная проверка тега)
				boolean hasNorth = world.getBlockState(BlockPos.containing(posX, posY, posZ - 1)).is(BlockTags.create(ResourceLocation.parse("ssc14:tables")));
				boolean hasEast = world.getBlockState(BlockPos.containing(posX + 1, posY, posZ)).is(BlockTags.create(ResourceLocation.parse("ssc14:tables")));
				boolean hasSouth = world.getBlockState(BlockPos.containing(posX, posY, posZ + 1)).is(BlockTags.create(ResourceLocation.parse("ssc14:tables")));
				boolean hasWest = world.getBlockState(BlockPos.containing(posX - 1, posY, posZ)).is(BlockTags.create(ResourceLocation.parse("ssc14:tables")));
				// Маппинг по твоей таблице
				int[] mapping = {0, 1, 2, 5, 3, 9, 6, 12, 4, 8, 10, 13, 7, 11, 14, 15};
				int mask = (hasNorth ? 1 : 0) | (hasEast ? 2 : 0) | (hasSouth ? 4 : 0) | (hasWest ? 8 : 0);
				int connectionValue = mapping[mask];
				// Устанавливаем состояние БЕЗ триггера соседей
				IntegerProperty property = (IntegerProperty) blockState.getBlock().getStateDefinition().getProperty("blockstate");
				if (property != null && property.getPossibleValues().contains(connectionValue)) {
					world.setBlock(pos, blockState.setValue(property, connectionValue), 2); // Флаг 2 = без updateNeighbors
				}
			}
		}
	}
}