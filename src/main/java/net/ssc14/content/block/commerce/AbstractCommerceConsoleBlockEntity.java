package net.ssc14.content.block.commerce;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Shared persistence and sided-inventory contract for commerce consoles. */
public abstract class AbstractCommerceConsoleBlockEntity
        extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private final CommerceConsoleIntegrationService.ConsoleKind kind;
    private final String defaultName;
    private final String displayName;
    private final int maxStackSize;
    private final int slotCount;
    private NonNullList<ItemStack> stacks;

    protected AbstractCommerceConsoleBlockEntity(BlockEntityType<?> type, BlockPos pos,
            BlockState state, CommerceConsoleIntegrationService.ConsoleKind kind,
            String defaultName, String displayName, int slotCount, int maxStackSize) {
        super(type, pos, state);
        this.kind = kind;
        this.defaultName = defaultName;
        this.displayName = displayName;
        this.slotCount = slotCount;
        this.maxStackSize = maxStackSize;
        this.stacks = NonNullList.withSize(slotCount, ItemStack.EMPTY);
    }

    @Override public void preRemoveSideEffects(BlockPos pos, BlockState state) { }
    @Override public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!tryLoadLootTable(input)) stacks = NonNullList.withSize(slotCount, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, stacks);
    }
    @Override public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!trySaveLootTable(output)) ContainerHelper.saveAllItems(output, stacks);
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
    @Override public int getMaxStackSize() { return maxStackSize; }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return CommerceConsoleIntegrationService.createMenu(kind, id, inventory, worldPosition);
    }
    @Override public Component getDisplayName() { return Component.literal(displayName); }
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
