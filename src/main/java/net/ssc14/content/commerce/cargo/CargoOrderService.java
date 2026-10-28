package net.ssc14.content.commerce.cargo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.menu.commerce.OrderGUIMenu;

import java.util.List;

public final class CargoOrderService {
    public static final int PREVIEW_SLOT_COUNT = 10;
    private static final String TAB_TAG = "TAB";

    private CargoOrderService() {
    }

    public static int currentTab(LevelAccessor world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? 0 : (int) blockEntity.getPersistentData().getDoubleOr(TAB_TAG, 0);
    }

    public static boolean selectTab(LevelAccessor world, BlockPos pos, int buttonId) {
        int tab = CargoOrderButtonIds.tabForButton(buttonId);
        if (world.isClientSide() || tab < 0) {
            return false;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity == null) {
            return false;
        }
        blockEntity.getPersistentData().putDouble(TAB_TAG, tab);
        blockEntity.setChanged();
        if (world instanceof Level level) {
            BlockState state = level.getBlockState(pos);
            level.sendBlockUpdated(pos, state, state, 3);
        }
        return true;
    }

    public static void refreshPreview(OrderGUIMenu menu, Player player) {
        List<CargoCatalog.CargoItem> items = CargoCatalog.getItemsForTab(
                currentTab(menu.world, new BlockPos(menu.x, menu.y, menu.z)));
        for (int slotId = 0; slotId < PREVIEW_SLOT_COUNT; slotId++) {
            ItemStack stack = slotId < items.size() ? items.get(slotId).getStack() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                stack.setCount(1);
            }
            menu.getSlots().get(slotId).set(stack);
        }
        player.containerMenu.broadcastChanges();
    }
}
