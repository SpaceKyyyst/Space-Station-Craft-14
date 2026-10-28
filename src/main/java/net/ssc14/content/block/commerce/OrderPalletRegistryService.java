package net.ssc14.content.block.commerce;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/** Port from canonical order-pallet content to persistent world registry storage. */
public final class OrderPalletRegistryService {
    @FunctionalInterface
    public interface Adapter {
        void register(LevelAccessor level, BlockPos pos);
    }

    private static Adapter adapter = (level, pos) -> { };

    private OrderPalletRegistryService() { }

    public static void install(Adapter nextAdapter) {
        adapter = Objects.requireNonNull(nextAdapter, "nextAdapter");
    }

    public static void register(LevelAccessor level, BlockPos pos) {
        if (level != null && !level.isClientSide()) adapter.register(level, pos);
    }
}
