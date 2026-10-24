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
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Zero-slot cable block entity retaining energy telemetry for the multitool UI. */
public class SheathingBlockEntity extends RandomizableContainerBlockEntity
        implements WorldlyContainer {
    public static final int SLOT_COUNT = 0;
    public static final int MAX_STACK_SIZE = 1;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private long currentPower;
    private long theoreticalSupply;
    private long idealConsumption;
    private long outputStored;
    private long outputMax;
    private int activeLayerMode;

    public SheathingBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14CableBlockEntities.SHEATHING.get(), pos, state);
    }

    public void setLayerNetworkData(int layerMode, long currentPower,
            long theoreticalSupply, long idealConsumption, long outputStored, long outputMax) {
        activeLayerMode = layerMode;
        this.currentPower = currentPower;
        this.theoreticalSupply = theoreticalSupply;
        this.idealConsumption = idealConsumption;
        this.outputStored = outputStored;
        this.outputMax = outputMax;
        setChanged();
    }

    @Override public void onLoad() {
        super.onLoad();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public void setBlockState(BlockState state) {
        BlockState previous = getBlockState();
        super.setBlockState(state);
        if (SheathingBlock.cableLayersChanged(previous, state)) {
            CableTopologyService.queueUpdate(level, worldPosition);
        }
    }

    @Override public void setRemoved() {
        super.setRemoved();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public void onChunkUnloaded() {
        super.onChunkUnloaded();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!tryLoadLootTable(input)) {
            stacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(input, stacks);
        activeLayerMode = input.getIntOr("activeLayerMode", 0);
        currentPower = input.getLongOr("currentPower", 0L);
        theoreticalSupply = input.getLongOr("theoreticalSupply", 0L);
        idealConsumption = input.getLongOr("idealConsumption", 0L);
        outputStored = input.getLongOr("outputStored", 0L);
        outputMax = input.getLongOr("outputMax", 0L);
    }

    @Override public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, stacks);
        }
        output.putInt("activeLayerMode", activeLayerMode);
        output.putLong("currentPower", currentPower);
        output.putLong("theoreticalSupply", theoreticalSupply);
        output.putLong("idealConsumption", idealConsumption);
        output.putLong("outputStored", outputStored);
        output.putLong("outputMax", outputMax);
    }

    @Override public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override public int getContainerSize() { return stacks.size(); }
    @Override public boolean isEmpty() { return true; }
    @Override public Component getDefaultName() { return Component.literal("sheathing"); }
    @Override public Component getDisplayName() { return Component.literal("Sheathing"); }
    @Override public int getMaxStackSize() { return MAX_STACK_SIZE; }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory) {
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
