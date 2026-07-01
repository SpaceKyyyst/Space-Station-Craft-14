
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
import net.mcreator.ssc.IEnergyStorageBlock;
import net.mcreator.ssc.block.APCBlock;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class APCBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, IEnergyStorageBlock {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
	
	// --- МАТЕМАТИКА ИЗ SS14 POW3R ---
	private long storedEnergy = 0;
	private static final long MAX_ENERGY = 2500000; // Джоули

	// Параметры сети
	private long netCurrent = 0;
	private long netSupply = 0;
	private long netConsume = 0;

	// Параметры автомата защиты ЛКП (Трип-система)
	private boolean mainBreakerEnabled = true; // Главный рубильник
	private boolean tripFlag = false;          // Флаг аварийного выбивания
	private int overloadTicks = 0;             // Счетчик тиков перегрузки
	private static final int TRIP_TICKS_THRESHOLD = 60; // 3 секунды (3 * 20 тиков)
	private static final long MAX_LOAD_WATT = 20000;    // Макс. нагрузка (20 кВт)

	// Переменные для отслеживания дельты энергии в текущем тике
	public long lastTickReceivingJoules = 0;
	public long lastTickDischargingJoules = 0;

	public APCBlockEntity(BlockPos position, BlockState state) {
		super(Ssc14ModBlockEntities.APC.get(), position, state);
	}

	/**
	 * Серверный логический тик ЛКП (вызывается из симулятора раз в тик).
	 * Обрабатывает таймеры перегрузки и обновляет визуальное состояние блока.
	 */
	public void serverTick() {
		if (this.level == null || this.level.isClientSide()) return;

		// 1. Логика автомата перегрузки (Trip Logic из ApcSystem.cs)
		// Переводим текущее идеальное потребление субрегиона в Ватты
		long currentLoadWatt = this.netConsume; 

		if (this.mainBreakerEnabled && currentLoadWatt > MAX_LOAD_WATT) {
			this.overloadTicks++;
			if (this.overloadTicks >= TRIP_TICKS_THRESHOLD) {
				this.tripFlag = true;
				this.mainBreakerEnabled = false; // Выбиваем автомат!
				this.overloadTicks = 0;
				// Здесь можно проиграть кастомный звук щелчка автомата
			}
		} else {
			this.overloadTicks = Math.max(0, this.overloadTicks - 1); // Постепенно остужаем автомат
		}

		// 2. Обновление blockstate (Визуальные индикаторы ЛКП)
		// В оригинальном коде стейты: 0 = норма/выключен, 1 = авария/разряжен и т.д.
		// Используем твой IntegerProperty BLOCKSTATE (0 = ок, 1 = авария/выбит)
		BlockState currentState = this.getBlockState();
		int targetState = (this.tripFlag || !this.mainBreakerEnabled || this.storedEnergy == 0) ? 1 : 0;
		
		if (currentState.hasProperty(APCBlock.BLOCKSTATE) && currentState.getValue(APCBlock.BLOCKSTATE) != targetState) {
			this.level.setBlock(this.worldPosition, currentState.setValue(APCBlock.BLOCKSTATE, targetState), 3);
		}
	}

	// Геттеры и сеттеры автомата защиты
	public boolean isMainBreakerEnabled() { return this.mainBreakerEnabled; }
	public void setMainBreakerEnabled(boolean state) { 
		this.mainBreakerEnabled = state; 
		if (state) this.tripFlag = false; // Сброс аварии при ручном включении
		this.setChanged();
	}
	public boolean isTripFlag() { return this.tripFlag; }

	@Override
	public long getStoredEnergy() { return this.storedEnergy; }

	@Override
	public void setStoredEnergy(long joules) {
		this.storedEnergy = Math.clamp(joules, 0, MAX_ENERGY);
		this.setChanged();
		if (this.level != null && !this.level.isClientSide()) {
			this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void setStoredEnergySimulation(long joules) {
		this.storedEnergy = Math.clamp(joules, 0, MAX_ENERGY);
		this.setChanged();
	}

	@Override
	public long getMaxEnergy() { return MAX_ENERGY; }

	@Override
	public long getNetworkCurrentPower() { return this.netCurrent; }
	@Override
	public void setNetworkCurrentPower(long watt) { this.netCurrent = watt; this.setChanged(); }
	@Override
	public long getNetworkTheoreticalSupply() { return this.netSupply; }
	@Override
	public void setNetworkTheoreticalSupply(long watt) { this.netSupply = watt; }
	@Override
	public long getNetworkIdealConsumption() { return this.netConsume; }
	@Override
	public void setNetworkIdealConsumption(long watt) { this.netConsume = watt; }

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		if (!this.tryLoadLootTable(valueInput))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(valueInput, this.stacks);
		
		this.storedEnergy = valueInput.getLongOr("storedEnergy", 0L);
		this.netCurrent = valueInput.getLongOr("netCurrent", 0L);
		this.netSupply = valueInput.getLongOr("netSupply", 0L);
		this.netConsume = valueInput.getLongOr("netConsume", 0L);
		
		this.mainBreakerEnabled = valueInput.getBooleanOr("mainBreaker", true);
		this.tripFlag = valueInput.getBooleanOr("tripFlag", false);
		this.overloadTicks = valueInput.getIntOr("overloadTicks", 0);
	}

	@Override
	public void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		if (!this.trySaveLootTable(valueOutput))
			ContainerHelper.saveAllItems(valueOutput, this.stacks);
			
		valueOutput.putLong("storedEnergy", this.storedEnergy);
		valueOutput.putLong("netCurrent", this.netCurrent);
		valueOutput.putLong("netSupply", this.netSupply);
		valueOutput.putLong("netConsume", this.netConsume);
		
		valueOutput.putBoolean("mainBreaker", this.mainBreakerEnabled);
		valueOutput.putBoolean("tripFlag", this.tripFlag);
		valueOutput.putInt("overloadTicks", this.overloadTicks);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) { return this.saveWithFullMetadata(lookupProvider); }

	@Override
	public int getContainerSize() { return stacks.size(); }

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty()) return false;
		return true;
	}

	@Override
	public Component getDefaultName() { return Component.literal("apc"); }

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) { return ChestMenu.threeRows(id, inventory); }

	@Override
	public Component getDisplayName() { return Component.literal("APC"); }

	@Override
	protected NonNullList<ItemStack> getItems() { return this.stacks; }

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) { this.stacks = stacks; }

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) { return true; }

	@Override
	public int[] getSlotsForFace(Direction side) { return IntStream.range(0, this.getContainerSize()).toArray(); }

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemstack, @Nullable Direction direction) { return this.canPlaceItem(index, itemstack); }

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack itemstack, Direction direction) { return true; }
}
