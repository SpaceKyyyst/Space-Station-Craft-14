package net.ssc14.content.block.communication;

import io.netty.buffer.Unpooled;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.ssc14.content.menu.station.CommunConsoleGUIMenu;
import net.ssc14.content.sound.ConsoleMenuSoundService;

/** Canonical communication-console menu and sound integration boundary. */
public final class CommunicationConsoleIntegrationService {
    public interface Adapter {
        void use(Level level, BlockPos pos, Player player);
        AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos);
    }

    private static Adapter adapter = new CanonicalAdapter();

    private CommunicationConsoleIntegrationService() { }

    private static final class CanonicalAdapter implements Adapter {
        @Override
        public void use(Level level, BlockPos pos, Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.literal("Communication console");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(
                            int id, Inventory inventory, Player menuPlayer) {
                        return CanonicalAdapter.this.createMenu(id, inventory, pos);
                    }
                }, pos);
            }
        }

        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
            FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos);
            return new CommunConsoleGUIMenu(id, inventory, data);
        }
    }

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
