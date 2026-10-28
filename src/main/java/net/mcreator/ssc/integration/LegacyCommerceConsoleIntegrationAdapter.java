package net.mcreator.ssc.integration;

import io.netty.buffer.Unpooled;
import java.util.Objects;
import net.mcreator.ssc.world.inventory.OrderGUIMenu;
import net.ssc14.content.menu.commerce.SaleGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.content.block.commerce.CommerceConsoleIntegrationService;

/** Connects canonical commerce consoles to the remaining legacy menus and sound procedure. */
public final class LegacyCommerceConsoleIntegrationAdapter
        implements CommerceConsoleIntegrationService.Adapter {
    @FunctionalInterface
    public interface SoundPlayer {
        void play(LevelAccessor level, double x, double y, double z);
    }

    private final SoundPlayer soundPlayer;

    public LegacyCommerceConsoleIntegrationAdapter(SoundPlayer soundPlayer) {
        this.soundPlayer = Objects.requireNonNull(soundPlayer, "soundPlayer");
    }

    @Override
    public void use(CommerceConsoleIntegrationService.ConsoleKind kind, Level level,
            BlockPos pos, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal(displayName(kind));
                }

                @Override
                public AbstractContainerMenu createMenu(
                        int id, Inventory inventory, Player menuPlayer) {
                    return LegacyCommerceConsoleIntegrationAdapter.this.createMenu(
                            kind, id, inventory, pos);
                }
            }, pos);
        }
        soundPlayer.play(level, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public AbstractContainerMenu createMenu(CommerceConsoleIntegrationService.ConsoleKind kind,
            int id, Inventory inventory, BlockPos pos) {
        FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos);
        return kind == CommerceConsoleIntegrationService.ConsoleKind.ORDER
                ? new OrderGUIMenu(id, inventory, data)
                : new SaleGUIMenu(id, inventory, data);
    }

    private static String displayName(CommerceConsoleIntegrationService.ConsoleKind kind) {
        return kind == CommerceConsoleIntegrationService.ConsoleKind.ORDER
                ? "Order console" : "Sale console";
    }
}
