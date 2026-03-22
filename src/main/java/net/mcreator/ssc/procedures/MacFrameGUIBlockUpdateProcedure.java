package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

import java.util.Map;
import java.util.HashMap;

public class MacFrameGUIBlockUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		{
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty _booleanProp)
				world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
		}
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockState currentState = world.getBlockState(pos);
		// 1. Получаем доступ к инвентарю ОДИН раз (кэшируем Capability)
		IItemHandler handler = getItemHandler(world, pos);
		if (handler == null) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		// 2. Проверяем плату в слоте 0
		ItemStack boardStack = handler.getStackInSlot(0);
		if (boardStack.isEmpty()) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		Item boardItem = boardStack.getItem();
		Map<Item, Integer> requirements = getRecipeRequirements(boardItem);
		// Если для этой платы нет рецепта
		if (requirements == null || requirements.isEmpty()) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		// 3. Считаем предметы в слотах 1-8
		Map<Item, Integer> foundItems = new HashMap<>();
		int totalItemsInSlots = 0;
		for (int slot = 1; slot <= 8; slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				int count = stack.getCount();
				totalItemsInSlots += count;
				foundItems.merge(stack.getItem(), count, Integer::sum);
			}
		}
		// 4. Проверка соответствия
		boolean isReady = true;
		int requiredTotalCount = 0;
		// Проверяем, что каждого нужного предмета ровно столько, сколько надо
		for (Map.Entry<Item, Integer> entry : requirements.entrySet()) {
			Item requiredItem = entry.getKey();
			int requiredCount = entry.getValue();
			requiredTotalCount += requiredCount;
			int foundCount = foundItems.getOrDefault(requiredItem, 0);
			if (foundCount != requiredCount) {
				isReady = false;
				break;
			}
		}
		// 5. Проверка "Не больше, не меньше" (чтобы не было мусора в слотах)
		// Общее количество предметов в слотах 1-8 должно совпадать с суммой требований рецепта
		if (isReady && totalItemsInSlots != requiredTotalCount) {
			isReady = false;
		}
		// 6. Обновляем состояние блока
		setReadyState(world, pos, currentState, isReady);
	}

	// Вспомогательный метод для получения рецепта в зависимости от платы
	private static Map<Item, Integer> getRecipeRequirements(Item board) {
		Map<Item, Integer> recipe = new HashMap<>();
		if (board == Ssc14ModItems.SUBSTATION_BOARD.get()) {
			recipe.put(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(), 5);
			recipe.put(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 5);
			recipe.put(Ssc14ModItems.LOW_BATTERIE.get(), 1);
			recipe.put(Ssc14ModItems.CAPACITOR.get(), 1);
		} else if (board == Ssc14ModItems.SMES_BOARD.get()) {
			recipe.put(Ssc14ModItems.CAPACITOR.get(), 1);
			recipe.put(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 10);
			// 4 батарейки в сумме, не важно в каких слотах
			recipe.put(Ssc14ModItems.LOW_BATTERIE.get(), 4);
		} else {
			return null;
		}
		return recipe;
	}

	// Вспомогательный метод для безопасного обновления свойства ready
	private static void setReadyState(LevelAccessor world, BlockPos pos, BlockState currentState, boolean ready) {
		// Проверяем, существует ли свойство, чтобы избежать крашей на кастомных блоках
		if (currentState.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty prop) {
			// Оптимизация: не ставим блок, если значение не изменилось
			if (currentState.getValue(prop) != ready) {
				world.setBlock(pos, currentState.setValue(prop, ready), 3);
			}
		}
	}

	// Оптимизированное получение хендлера (кэширование проверки типа)
	private static IItemHandler getItemHandler(LevelAccessor world, BlockPos pos) {
		if (world instanceof ILevelExtension ext) {
			return ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		}
		return null;
	}
}