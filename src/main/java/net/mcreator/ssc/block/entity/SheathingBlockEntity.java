
package net.mcreator.ssc.block.entity;

import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlockEntities;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class SheathingBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(0, ItemStack.EMPTY);

	// --- ДАННЫЕ СЕТИ ДЛЯ МУЛЬТИТУЛА (ИСПРАВЛЕНО: Добавлены поля симуляции) ---
	private long currentPower = 0;
	private long theoreticalSupply = 0;
	private long idealConsumption = 0;
	private long outputStored = 0;
	private long outputMax = 0;
	private int activeLayerMode = 0;

	public SheathingBlockEntity(BlockPos position, BlockState state) {
		super(Ssc14ModBlockEntities.SHEATHING.get(), position, state);
	}

	/**
	 * Записывает актуальные данные энергосети текущего слоя в кабельную обшивку.
	 * Вызывается каждый тик из симулятора EnergySimulationServer.
	 */
	public void setLayerNetworkData(int layerMode, long currentPower, long theoreticalSupply, long idealConsumption, long outputStored, long outputMax) {
		this.activeLayerMode = layerMode;
		this.currentPower = currentPower;
		this.theoreticalSupply = theoreticalSupply;
		this.idealConsumption = idealConsumption;
		this.outputStored = outputStored;
		this.outputMax = outputMax;
		this.setChanged();
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		if (!this.tryLoadLootTable(valueInput))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(valueInput, this.stacks);

		// ИСПРАВЛЕНО: Чтение NBT-данных сети через ValueInput для NeoForge 26.x
		this.activeLayerMode = valueInput.getIntOr("activeLayerMode", 0);
		this.currentPower = valueInput.getLongOr("currentPower", 0L);
		this.theoreticalSupply = valueInput.getLongOr("theoreticalSupply", 0L);
		this.idealConsumption = valueInput.getLongOr("idealConsumption", 0L);
		this.outputStored = valueInput.getLongOr("outputStored", 0L);
		this.outputMax = valueInput.getLongOr("outputMax", 0L);
	}

	@Override
	public void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		if (!this.trySaveLootTable(valueOutput))
			ContainerHelper.saveAllItems(valueOutput, this.stacks);

		// ИСПРАВЛЕНО: Сохранение NBT-данных сети через ValueOutput для NeoForge 26.x
		valueOutput.putInt("activeLayerMode", this.activeLayerMode);
		valueOutput.putLong("currentPower", this.currentPower);
		valueOutput.putLong("theoreticalSupply", this.theoreticalSupply);
		valueOutput.putLong("idealConsumption", this.idealConsumption);
		valueOutput.putLong("outputStored", this.outputStored);
		valueOutput.putLong("outputMax", this.outputMax);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		return this.saveWithFullMetadata(lookupProvider);
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return Component.literal("sheathing");
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Sheathing");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemstack, @Nullable Direction direction) {
		return this.canPlaceItem(index, itemstack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack itemstack, Direction direction) {
		return true;
	}
}
