package net.ssc14.content.block.commerce;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

/** Boundary for the legacy vending procedures and menu. */
public final class VendingMachineIntegrationService {
    public interface Adapter {
        void onPlaced(Level level, BlockPos pos);
        void onNeighborChanged(Level level, BlockPos pos);
        void onAttack(Level level, BlockPos pos, Player player);
        void open(Level level, BlockPos pos, Player player);
        AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos);
    }

    private static Adapter adapter = new Adapter() {
        @Override public void onPlaced(Level level, BlockPos pos) { }
        @Override public void onNeighborChanged(Level level, BlockPos pos) { }
        @Override public void onAttack(Level level, BlockPos pos, Player player) { }
        @Override public void open(Level level, BlockPos pos, Player player) { }
        @Override public AbstractContainerMenu createMenu(
                int id, Inventory inventory, BlockPos pos) { return null; }
    };

    private VendingMachineIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void onPlaced(Level level, BlockPos pos) { adapter.onPlaced(level, pos); }
    public static void onNeighborChanged(Level level, BlockPos pos) {
        adapter.onNeighborChanged(level, pos);
    }
    public static void onAttack(Level level, BlockPos pos, Player player) {
        adapter.onAttack(level, pos, player);
    }
    public static void open(Level level, BlockPos pos, Player player) {
        adapter.open(level, pos, player);
    }
    public static AbstractContainerMenu createMenu(
            int id, Inventory inventory, BlockPos pos) {
        return adapter.createMenu(id, inventory, pos);
    }
}
