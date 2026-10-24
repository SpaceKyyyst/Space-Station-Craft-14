package net.ssc14.content.block.storage;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Canonical state transitions for storing and retrieving a cabinet extinguisher. */
public final class ExtinguisherCabinetInteractionService {
    private static Supplier<Item> extinguisher = () -> Items.AIR;
    private static Supplier<Item> openExtinguisher = () -> Items.AIR;

    private ExtinguisherCabinetInteractionService() { }

    public static void install(Supplier<Item> extinguisherSupplier,
            Supplier<Item> openExtinguisherSupplier) {
        extinguisher = Objects.requireNonNull(extinguisherSupplier, "extinguisherSupplier");
        openExtinguisher = Objects.requireNonNull(
                openExtinguisherSupplier, "openExtinguisherSupplier");
    }

    public static void interact(Level level, BlockPos pos, BlockState state, Player player) {
        if (!state.getValue(ExtinguisherCabinetBlock.OPEN)) {
            set(level, pos, ExtinguisherCabinetBlock.OPEN, true);
            return;
        }
        if (player.isShiftKeyDown()) {
            set(level, pos, ExtinguisherCabinetBlock.OPEN, false);
            return;
        }

        ItemStack held = player.getMainHandItem();
        if (!state.getValue(ExtinguisherCabinetBlock.EMPTY) && held.isEmpty()) {
            set(level, pos, ExtinguisherCabinetBlock.EMPTY, true);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(extinguisher.get()));
            player.getInventory().setChanged();
        } else if (state.getValue(ExtinguisherCabinetBlock.EMPTY)
                && (held.is(extinguisher.get()) || held.is(openExtinguisher.get()))) {
            held.shrink(1);
            set(level, pos, ExtinguisherCabinetBlock.EMPTY, false);
        } else {
            set(level, pos, ExtinguisherCabinetBlock.OPEN, false);
        }
    }

    private static void set(Level level, BlockPos pos, BooleanProperty property, boolean value) {
        level.setBlock(pos, level.getBlockState(pos).setValue(property, value), 3);
    }
}
