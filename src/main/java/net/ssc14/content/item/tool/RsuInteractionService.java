package net.ssc14.content.item.tool;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/** Boundary for the RSU runtime retained during incremental migration. */
public final class RsuInteractionService {
    private static Adapter adapter = Adapter.NO_OP;

    private RsuInteractionService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static void use(Level level, Player player, InteractionHand hand,
            ItemStack stack) {
        adapter.use(level, player, hand, stack);
    }

    public static void useOn(UseOnContext context) {
        adapter.useOn(context);
    }

    public static void inventoryTick(ServerLevel level, Entity entity,
            ItemStack stack) {
        adapter.inventoryTick(level, entity, stack);
    }

    public interface Adapter {
        Adapter NO_OP = new Adapter() { };

        default void use(Level level, Player player, InteractionHand hand,
                ItemStack stack) { }

        default void useOn(UseOnContext context) { }

        default void inventoryTick(ServerLevel level, Entity entity,
                ItemStack stack) { }
    }
}
