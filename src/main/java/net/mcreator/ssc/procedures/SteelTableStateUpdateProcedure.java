
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

import net.mcreator.ssc.init.Ssc14ModBlocks;

public class SteelTableStateUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		int centerX = (int) Math.floor(x);
		int centerY = (int) Math.floor(y);
		int centerZ = (int) Math.floor(z);
		int[][] offsets = {{0, 0},
				{0, -1},
				{1, 0},
				{0, 1},
				{-1, 0}
		};
		for (int[] offset : offsets) {
			int posX = centerX + offset[0];
			int posY = centerY;
			int posZ = centerZ + offset[1];
			BlockPos pos = new BlockPos(posX, posY, posZ);
			BlockState blockState = world.getBlockState(pos);
			
			// ИСПРАВЛЕНО: Добавлен фабричный метод вместо старого парсинга
			if (!blockState.is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tables")))) {
				continue;
			}
			
			// ИСПРАВЛЕНО: Безопасное обновление тегов для всех четырех направлений стола
			boolean hasNorth = world.getBlockState(BlockPos.containing(posX, posY, posZ - 1)).is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tables")));
			boolean hasEast = world.getBlockState(BlockPos.containing(posX + 1, posY, posZ)).is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tables")));
			boolean hasSouth = world.getBlockState(BlockPos.containing(posX, posY, posZ + 1)).is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tables")));
			boolean hasWest = world.getBlockState(BlockPos.containing(posX - 1, posY, posZ)).is(BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "tables")));
			
			int[] mapping = {0, 1, 2, 5, 3, 9, 6, 12, 4, 8, 10, 13, 7, 11, 14, 15};
			int mask = (hasNorth ? 1 : 0) | (hasEast ? 2 : 0) | (hasSouth ? 4 : 0) | (hasWest ? 8 : 0);
			int connectionValue = mapping[mask];
			
			IntegerProperty property = (IntegerProperty) blockState.getBlock().getStateDefinition().getProperty("blockstate");
			if (property != null && property.getPossibleValues().contains(connectionValue)) {
				world.setBlock(pos, blockState.setValue(property, connectionValue), 2);
			}
		}
	}
}
