package net.ssc14.content.block.machinery;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Boundary for the legacy microwave menu implementation. */
public final class MicrowaveMenuService {
    @FunctionalInterface
    public interface Factory {
        AbstractContainerMenu create(int id, Inventory inventory, BlockPos pos);
    }

    private static Factory factory = (id, inventory, pos) -> null;

    private MicrowaveMenuService() { }

    public static void install(Factory replacement) {
        factory = Objects.requireNonNull(replacement, "replacement");
    }

    public static AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
        return factory.create(id, inventory, pos);
    }
}
