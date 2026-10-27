package net.ssc14.content.item.tool;

import java.util.Objects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Owns controller modes and bridges block interaction to the legacy airlock runtime. */
public final class AirlockControllerService {
    private static final String MODE_KEY = "mode";
    private static final int MODE_COUNT = 3;
    private static Adapter adapter = (level, x, y, z, state, entity, stack) -> { };

    private AirlockControllerService() { }

    public static Adapter install(Adapter replacement) {
        Adapter previous = adapter;
        adapter = Objects.requireNonNull(replacement, "replacement");
        return previous;
    }

    public static int mode(ItemStack stack) {
        return (int) rawMode(stack);
    }

    public static void switchMode(ItemStack stack) {
        double current = rawMode(stack);
        if (current != 0.0D && current != 1.0D && current != 2.0D) {
            return;
        }
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(MODE_KEY, (current + 1.0D) % MODE_COUNT));
    }

    public static void showMode(Entity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }
        double current = rawMode(stack);
        Component message = current == 0.0D
                ? Component.literal("Режим:  Болты")
                : current == 1.0D
                        ? Component.literal("Режим:  Аварийный")
                        : current == 2.0D
                                ? Component.literal("Режим:  Открытие-Закрытие")
                                : null;
        if (message != null) {
            player.sendSystemMessage(message, true);
        }
    }

    public static void interact(UseOnContext context) {
        var pos = context.getClickedPos();
        interact(context.getLevel(), pos.getX(), pos.getY(), pos.getZ(),
                context.getLevel().getBlockState(pos), context.getPlayer(),
                context.getItemInHand());
    }

    public static void interact(LevelAccessor level, double x, double y, double z,
            BlockState state, Entity entity, ItemStack stack) {
        adapter.interact(level, x, y, z, state, entity, stack);
    }

    private static double rawMode(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(MODE_KEY, 0.0D);
    }

    @FunctionalInterface
    public interface Adapter {
        void interact(LevelAccessor level, double x, double y, double z,
                BlockState state, Entity entity, ItemStack stack);
    }
}
