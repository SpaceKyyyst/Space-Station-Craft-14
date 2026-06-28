
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

	public final long[] layerCurrent = new long[4];
	public final long[] layerSupply = new long[4];
	public final long[] layerConsume = new long[4];
	public final long[] layerStored = new long[4];
	public final long[] layerMax = new long[4];

	public long netCurrent = 0;
	public long netSupply = 0;
	public long netConsume = 0;
	public long storedEnergy = 0;
	public long maxEnergy = 0;

	public SheathingBlockEntity(BlockPos position, BlockState state) {
		super(Ssc14ModBlockEntities.SHEATHING.get(), position, state);
	}

	public void setLayerNetworkData(int layer, long current, long supply, long consume, long stored, long max) {
		if (layer < 1 || layer > 3) return;
		
		this.layerCurrent[layer] = current;
		this.layerSupply[layer] = supply;
		this.layerConsume[layer] = consume;
		this.layerStored[layer] = stored;
		this.layerMax[layer] = max;

		this.netCurrent = current;
		this.netSupply = supply;
		this.netConsume = consume;
		this.storedEnergy = stored;
		this.maxEnergy = max;

		this.setChanged();
		if (this.level != null && !this.level.isClientSide()) {
			this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void setNetworkData(long current, long supply, long consume, long stored, long max) {
		setLayerNetworkData(1, current, supply, consume, stored, max);
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		if (!this.tryLoadLootTable(valueInput))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(valueInput, this.stacks);
		
		for (int i = 1; i <= 3; i++) {
			this.layerCurrent[i] = valueInput.getLongOr("layerCurrent_" + i, 0L);
			this.layerSupply[i] = valueInput.getLongOr("layerSupply_" + i, 0L);
			this.layerConsume[i] = valueInput.getLongOr("layerConsume_" + i, 0L);
			this.layerStored[i] = valueInput.getLongOr("layerStored_" + i, 0L);
			this.layerMax[i] = valueInput.getLongOr("layerMax_" + i, 0L);
		}

		this.netCurrent = this.layerCurrent[1];
		this.netSupply = this.layerSupply[1];
		this.netConsume = this.layerConsume[1];
		this.storedEnergy = this.layerStored[1];
		this.maxEnergy = this.layerMax[1];
	}

	@Override
	public void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		if (!this.trySaveLootTable(valueOutput))
			ContainerHelper.saveAllItems(valueOutput, this.stacks);
			
		for (int i = 1; i <= 3; i++) {
			valueOutput.putLong("layerCurrent_" + i, this.layerCurrent[i]);
			valueOutput.putLong("layerSupply_" + i, this.layerSupply[i]);
			valueOutput.putLong("layerConsume_" + i, this.layerConsume[i]);
			valueOutput.putLong("layerStored_" + i, this.layerStored[i]);
			valueOutput.putLong("layerMax_" + i, this.layerMax[i]);
		}
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
	protected Component getDefaultName() { // ИСПРАВЛЕНО: добавили protected
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
