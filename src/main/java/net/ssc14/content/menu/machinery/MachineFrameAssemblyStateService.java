
package net.ssc14.content.menu.machinery;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;

import net.ssc14.content.item.Ssc14Items;

import java.util.Map;
import java.util.HashMap;

public final class MachineFrameAssemblyStateService {
	private MachineFrameAssemblyStateService() {
	}

	public static void refresh(LevelAccessor world, BlockPos pos) {
		{
			BlockPos _pos = pos;
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty _booleanProp)
				world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
		}
		BlockState currentState = world.getBlockState(pos);
		
		ResourceHandler<ItemResource> handler = getItemHandler(world, pos);
		if (handler == null) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		
		ItemStack boardStack = ItemUtil.getStack(handler, 0);
		if (boardStack.isEmpty()) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		Item boardItem = boardStack.getItem();
		Map<Item, Integer> requirements = getRecipeRequirements(boardItem);
		
		if (requirements == null || requirements.isEmpty()) {
			setReadyState(world, pos, currentState, false);
			return;
		}
		
		Map<Item, Integer> foundItems = new HashMap<>();
		int totalItemsInSlots = 0;
		for (int slot = 1; slot <= 8; slot++) {
			ItemStack stack = ItemUtil.getStack(handler, slot);
			if (!stack.isEmpty()) {
				int count = stack.getCount();
				totalItemsInSlots += count;
				foundItems.merge(stack.getItem(), count, Integer::sum);
			}
		}
		
		boolean isReady = true;
		int requiredTotalCount = 0;
		
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
		
		if (isReady && totalItemsInSlots != requiredTotalCount) {
			isReady = false;
		}
		
		setReadyState(world, pos, currentState, isReady);
	}

	private static Map<Item, Integer> getRecipeRequirements(Item board) {
		Map<Item, Integer> recipe = new HashMap<>();
		if (board == Ssc14Items.SUBSTATION_BOARD.get()) {
			recipe.put(Ssc14Items.MEDIUM_VOLTAGE_CABLE.get(), 5);
			recipe.put(Ssc14Items.HIGH_VOLTAGE_CABLE.get(), 5);
			recipe.put(Ssc14Items.LOW_BATTERIE.get(), 1);
			recipe.put(Ssc14Items.CAPACITOR.get(), 1);
		} else if (board == Ssc14Items.SMES_BOARD.get()) {
			recipe.put(Ssc14Items.CAPACITOR.get(), 1);
			recipe.put(Ssc14Items.HIGH_VOLTAGE_CABLE.get(), 10);
			recipe.put(Ssc14Items.LOW_BATTERIE.get(), 4);
		} else {
			return null;
		}
		return recipe;
	}

	private static void setReadyState(LevelAccessor world, BlockPos pos, BlockState currentState, boolean ready) {
		if (currentState.getBlock().getStateDefinition().getProperty("ready") instanceof BooleanProperty prop) {
			if (currentState.getValue(prop) != ready) {
				world.setBlock(pos, currentState.setValue(prop, ready), 3);
			}
		}
	}

	private static ResourceHandler<ItemResource> getItemHandler(LevelAccessor world, BlockPos pos) {
		if (world instanceof Level level) {
			return level.getCapability(Capabilities.Item.BLOCK, pos, null);
		}
		return null;
	}
}
