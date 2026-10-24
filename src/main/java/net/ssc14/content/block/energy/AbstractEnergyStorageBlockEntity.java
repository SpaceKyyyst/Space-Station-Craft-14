package net.ssc14.content.block.energy;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Shared persisted storage and inventory behavior for stationary energy machines. */
public abstract class AbstractEnergyStorageBlockEntity
        extends RandomizableContainerBlockEntity implements WorldlyContainer, EnergyStorage {
    public static final int SLOT_COUNT = 9;
    private final long maxEnergy;
    private final String defaultName;
    private final String displayName;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private long storedEnergy;
    private long netCurrent;
    private long netSupply;
    private long netConsume;

    protected AbstractEnergyStorageBlockEntity(BlockEntityType<?> type, BlockPos pos,
            BlockState state, long maxEnergy, String defaultName, String displayName) {
        super(type, pos, state);
        this.maxEnergy = maxEnergy;
        this.defaultName = defaultName;
        this.displayName = displayName;
    }

    @Override public void onLoad() {
        super.onLoad();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
    @Override public void setRemoved() {
        super.setRemoved();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
    @Override public void onChunkUnloaded() {
        super.onChunkUnloaded();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public long getStoredEnergy() { return storedEnergy; }
    @Override public void setStoredEnergy(long joules) {
        long next = Math.clamp(joules, 0, maxEnergy);
        if (storedEnergy == next) return;
        storedEnergy = next;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    @Override public void setStoredEnergySimulation(long joules) {
        long next = Math.clamp(joules, 0, maxEnergy);
        if (storedEnergy == next) return;
        storedEnergy = next;
        setChanged();
    }
    @Override public long getMaxEnergy() { return maxEnergy; }
    @Override public long getNetworkCurrentPower() { return netCurrent; }
    @Override public void setNetworkCurrentPower(long watt) { netCurrent = watt; setChanged(); }
    @Override public long getNetworkTheoreticalSupply() { return netSupply; }
    @Override public void setNetworkTheoreticalSupply(long watt) { netSupply = watt; }
    @Override public long getNetworkIdealConsumption() { return netConsume; }
    @Override public void setNetworkIdealConsumption(long watt) { netConsume = watt; }

    @Override public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!tryLoadLootTable(input)) {
            stacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(input, stacks);
        storedEnergy = Math.clamp(input.getLongOr("storedEnergy", 0L), 0, maxEnergy);
        netCurrent = input.getLongOr("netCurrent", 0L);
        netSupply = input.getLongOr("netSupply", 0L);
        netConsume = input.getLongOr("netConsume", 0L);
    }

    @Override public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!trySaveLootTable(output)) ContainerHelper.saveAllItems(output, stacks);
        output.putLong("storedEnergy", storedEnergy);
        output.putLong("netCurrent", netCurrent);
        output.putLong("netSupply", netSupply);
        output.putLong("netConsume", netConsume);
    }

    @Override public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }
    @Override public int getContainerSize() { return stacks.size(); }
    @Override public boolean isEmpty() { return stacks.stream().allMatch(ItemStack::isEmpty); }
    @Override public Component getDefaultName() { return Component.literal(defaultName); }
    @Override public Component getDisplayName() { return Component.literal(displayName); }
    @Override protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }
    @Override protected NonNullList<ItemStack> getItems() { return stacks; }
    @Override protected void setItems(NonNullList<ItemStack> items) { stacks = items; }
    @Override public boolean canPlaceItem(int slot, ItemStack stack) { return true; }
    @Override public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, getContainerSize()).toArray();
    }
    @Override public boolean canPlaceItemThroughFace(int slot, ItemStack stack,
            @Nullable Direction direction) { return canPlaceItem(slot, stack); }
    @Override public boolean canTakeItemThroughFace(int slot, ItemStack stack,
            Direction direction) { return true; }
}
