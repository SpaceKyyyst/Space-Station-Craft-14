package net.ssc14.content.block.communication;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.ssc14.content.sound.ConsoleMenuSoundService;

/** Boundary for the legacy communication-console menu and sound implementation. */
public final class CommunicationConsoleIntegrationService {
    public interface Adapter {
        void use(Level level, BlockPos pos, Player player);
        AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos);
    }

    private static Adapter adapter = new Adapter() {
        @Override public void use(Level level, BlockPos pos, Player player) { }
        @Override public AbstractContainerMenu createMenu(
                int id, Inventory inventory, BlockPos pos) {
            return null;
        }
    };

    private CommunicationConsoleIntegrationService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static void use(Level level, BlockPos pos, Player player) {
        adapter.use(level, pos, player);
        if (level != null && pos != null) {
            ConsoleMenuSoundService.play(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public static AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
        return adapter.createMenu(id, inventory, pos);
    }
}
