package net.ssc14.content.block.airlock;

import io.netty.buffer.Unpooled;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.menu.access.AccessConfigMENUMenu;

/** Owns canonical airlock door lifecycle and menu integration. */
public final class AirlockDoorIntegrationService {
    private static Adapter adapter = new CanonicalAdapter();

    private AirlockDoorIntegrationService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void place(Level level, BlockPos pos, BlockState state) {
        adapter.place(level, pos, state);
    }
    public static void use(Level level, BlockPos pos, BlockState state, Player player) {
        adapter.use(level, pos, state, player);
    }
    public static void tick(ServerLevel level, BlockPos pos) { adapter.tick(level, pos); }
    public static void neighborChanged(Level level, BlockPos pos) {
        adapter.neighborChanged(level, pos);
    }
    public static AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
        return adapter.createMenu(id, inventory, pos);
    }

    public interface Adapter {
        void place(Level level, BlockPos pos, BlockState state);
        void use(Level level, BlockPos pos, BlockState state, Player player);
        void tick(ServerLevel level, BlockPos pos);
        void neighborChanged(Level level, BlockPos pos);
        AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos);
    }

    private static final class CanonicalAdapter implements Adapter {
        @Override
        public void place(Level level, BlockPos pos, BlockState state) {
            AirlockStateInitializer.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
        }

        @Override
        public void use(Level level, BlockPos pos, BlockState state, Player player) {
            AirlockLifecycleService.execute(level, pos.getX(), pos.getY(), pos.getZ(), state, player);
        }

        @Override
        public void tick(ServerLevel level, BlockPos pos) {
            AirlockLifecycleService.tick(level, pos);
        }

        @Override
        public void neighborChanged(Level level, BlockPos pos) {
            AirlockFloor.ensureLowerPart(level, pos);
            if (level instanceof ServerLevel server) AirlockSignalHandler.changed(server, pos);
        }

        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, BlockPos pos) {
            return new AccessConfigMENUMenu(id, inventory,
                    new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
        }
    }
}
