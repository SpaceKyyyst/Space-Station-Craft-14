package net.ssc14.content.block.access;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.menu.access.IDcodeMenu;
import net.ssc14.content.sound.ConsoleMenuSoundService;

/** Owns canonical ID-console menu, opening and sound integration. */
public final class IdConsoleIntegrationService {
    public interface Adapter {
        void use(Level level, BlockPos pos, Player player);
        AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos);
    }

    private static Adapter adapter = new CanonicalAdapter();

    private IdConsoleIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(Level level, BlockPos pos, Player player) {
        adapter.use(level, pos, player);
        if (level != null && pos != null) {
            ConsoleMenuSoundService.play(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public static AbstractContainerMenu createMenu(
            int id, Inventory inventory, BlockPos pos) {
        return adapter.createMenu(id, inventory, pos);
    }

    public static void resetBuffer(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;
        blockEntity.getPersistentData().putBoolean("Bufer_LG", false);
        BlockState state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, state, state, 3);
    }

    private static final class CanonicalAdapter implements Adapter {
        @Override
        public void use(Level level, BlockPos pos, Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.literal("Console Of ID-card");
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
            return new IDcodeMenu(id, inventory,
                    new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
        }
    }
}
