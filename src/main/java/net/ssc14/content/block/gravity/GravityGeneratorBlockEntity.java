package net.ssc14.content.block.gravity;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.ssc14.handy.api.BlockEntityUIProvider;
import com.ssc14.handy.window.WindowProperties;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Slot-free persistent state and UI provider for the gravity generator. */
public class GravityGeneratorBlockEntity extends RandomizableContainerBlockEntity
        implements WorldlyContainer, BlockEntityUIProvider {
    public static final int SLOT_COUNT = 0;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    public GravityGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(Ssc14GravityBlockEntities.GRAVITY_GENERATOR.get(), pos, state);
    }

    @Override
    public boolean hasAccess(Player player, GlobalPos source) {
        return GravityGeneratorUiService.canAccess(this, player, source);
    }

    @Override
    public ModularUI getUI(GlobalPos source, Player player) {
        return GravityGeneratorUiService.create(this, source, player);
    }

    @Override
    public WindowProperties getWindowProperties() {
        return GravityGeneratorUiService.windowProperties();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) { }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (!tryLoadLootTable(input)) {
            stacks = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(input, stacks);
    }

    @Override
    public void saveAdditional(ValueOutput output) {
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
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override public int getContainerSize() { return stacks.size(); }
    @Override public boolean isEmpty() { return stacks.stream().allMatch(ItemStack::isEmpty); }
    @Override public Component getDefaultName() { return Component.literal("gravity_generator"); }
    @Override public int getMaxStackSize() { return 1; }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return GravityGeneratorUiService.createMenu(this, id, inventory);
    }

    @Override public Component getDisplayName() { return Component.literal("Gravity Generator"); }
    @Override protected NonNullList<ItemStack> getItems() { return stacks; }
    @Override protected void setItems(NonNullList<ItemStack> items) { stacks = items; }
    @Override public boolean canPlaceItem(int slot, ItemStack stack) { return true; }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(
            int slot, ItemStack stack, @Nullable Direction direction) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return true;
    }
}
