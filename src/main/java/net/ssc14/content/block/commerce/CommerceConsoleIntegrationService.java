package net.ssc14.content.block.commerce;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.ssc14.content.sound.ConsoleMenuSoundService;

/** Boundary for the legacy commerce-console menus and open sound. */
public final class CommerceConsoleIntegrationService {
    public enum ConsoleKind { ORDER, SALE }

    public interface Adapter {
        void use(ConsoleKind kind, Level level, BlockPos pos, Player player);
        AbstractContainerMenu createMenu(
                ConsoleKind kind, int id, Inventory inventory, BlockPos pos);
    }

    private static Adapter adapter = new Adapter() {
        @Override public void use(ConsoleKind kind, Level level, BlockPos pos, Player player) { }
        @Override public AbstractContainerMenu createMenu(
                ConsoleKind kind, int id, Inventory inventory, BlockPos pos) {
            return null;
        }
    };

    private CommerceConsoleIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(ConsoleKind kind, Level level, BlockPos pos, Player player) {
        adapter.use(kind, level, pos, player);
        if (level != null && pos != null) {
            ConsoleMenuSoundService.play(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public static AbstractContainerMenu createMenu(
            ConsoleKind kind, int id, Inventory inventory, BlockPos pos) {
        return adapter.createMenu(kind, id, inventory, pos);
    }
}
