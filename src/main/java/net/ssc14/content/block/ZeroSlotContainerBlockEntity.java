package net.ssc14.content.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Shared compatibility container for blocks that expose an empty item capability. */
public abstract class ZeroSlotContainerBlockEntity
        extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public static final int SLOT_COUNT = 0;
    private final String defaultName;
    private final String displayName;
    private NonNullList<ItemStack> stacks = NonNullList.create();

    protected ZeroSlotContainerBlockEntity(BlockEntityType<?> type, BlockPos pos,
            BlockState state, String defaultName, String displayName) {
        super(type, pos, state);
        this.defaultName = defaultName;
        this.displayName = displayName;
    }

    @Override public void preRemoveSideEffects(BlockPos pos, BlockState state) { }
    @Override public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }
    @Override public int getContainerSize() { return SLOT_COUNT; }
    @Override public boolean isEmpty() { return true; }
    @Override public Component getDefaultName() { return Component.literal(defaultName); }
    @Override public Component getDisplayName() { return Component.literal(displayName); }
    @Override public int getMaxStackSize() { return 1; }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }
    @Override protected NonNullList<ItemStack> getItems() { return stacks; }
    @Override protected void setItems(NonNullList<ItemStack> items) { stacks = items; }
    @Override public boolean canPlaceItem(int slot, ItemStack stack) { return true; }
    @Override public int[] getSlotsForFace(Direction side) { return new int[0]; }
    @Override public boolean canPlaceItemThroughFace(int slot, ItemStack stack,
            @Nullable Direction direction) { return canPlaceItem(slot, stack); }
    @Override public boolean canTakeItemThroughFace(int slot, ItemStack stack,
            Direction direction) { return true; }
}
