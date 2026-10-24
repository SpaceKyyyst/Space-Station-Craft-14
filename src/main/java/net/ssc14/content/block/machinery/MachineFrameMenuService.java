package net.ssc14.content.block.machinery;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Legacy-neutral menu factory used by canonical machine-frame content. */
public final class MachineFrameMenuService {
    private static final Component TITLE = Component.literal("Machine Frame");
    private static volatile MenuFactory factory;

    private MachineFrameMenuService() { }

    public static void install(MenuFactory menuFactory) {
        factory = Objects.requireNonNull(menuFactory, "menuFactory");
    }

    public static void open(ServerPlayer player, BlockPos pos) {
        player.openMenu(new SimpleMenuProvider(
                (id, inventory, ignored) -> create(id, inventory, pos), TITLE), pos);
    }

    public static AbstractContainerMenu create(int id, Inventory inventory, BlockPos pos) {
        MenuFactory configured = factory;
        if (configured == null) {
            throw new IllegalStateException("Machine frame menu factory is not installed");
        }
        return configured.create(id, inventory, pos);
    }

    @FunctionalInterface
    public interface MenuFactory {
        AbstractContainerMenu create(int id, Inventory inventory, BlockPos pos);
    }
}
