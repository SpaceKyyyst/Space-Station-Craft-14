package net.ssc14.content.block.lighting;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
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

/** Zero-slot compatibility block entity shared by energy and emergency lights. */
public class LightingBlockEntity extends RandomizableContainerBlockEntity
        implements WorldlyContainer {
    private final Component defaultName;
    private final Component displayName;
    private final boolean energyAware;
    private NonNullList<ItemStack> stacks = NonNullList.create();

    public LightingBlockEntity(String registryPath, String displayName, boolean energyAware,
            BlockPos pos, BlockState state) {
        super(resolveType(registryPath), pos, state);
        this.defaultName = Component.literal(registryPath);
        this.displayName = Component.literal(displayName);
        this.energyAware = energyAware;
    }

    @SuppressWarnings("unchecked")
    private static BlockEntityType<LightingBlockEntity> resolveType(String registryPath) {
        return (BlockEntityType<LightingBlockEntity>) BuiltInRegistries.BLOCK_ENTITY_TYPE.getValue(
                Identifier.fromNamespaceAndPath("ssc_14", registryPath));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        queueEnergyUpdate();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        queueEnergyUpdate();
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        queueEnergyUpdate();
    }

    private void queueEnergyUpdate() {
        if (energyAware && level != null) {
            LightingEnergyService.queueUpdate(level, worldPosition);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!tryLoadLootTable(input)) {
            stacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(input, stacks);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, stacks);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        return saveWithFullMetadata(lookupProvider);
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    protected Component getDefaultName() {
        return defaultName;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        stacks = items;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack,
            @Nullable Direction direction) {
        return canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
}
