package net.ssc14.content.item.tool;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Owns the voltage-selection state stored by the legacy multitool. */
public final class MultitoolModeService {
    private static final String MODE_KEY = "Mode";
    private static final double MAX_MODE = 3.0D;

    private MultitoolModeService() { }

    public static int mode(ItemStack stack) {
        return (int) rawMode(stack);
    }

    public static void switchMode(Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        double current = rawMode(stack);
        double next = current < MAX_MODE ? current + 1.0D : 1.0D;
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(MODE_KEY, next));
        if (entity instanceof ServerPlayer player) {
            Component message = next == 1.0D
                    ? Component.literal("Текущий режим:  НВ")
                    : next == 2.0D
                            ? Component.literal("Текущий режим:  СВ")
                            : next == 3.0D
                                    ? Component.literal("Текущий режим:  ВВ")
                                    : null;
            if (message != null) {
                player.sendSystemMessage(message, true);
            }
        }
    }

    public static String description(ItemStack stack) {
        return rawMode(stack) == 1.0D ? "Текущий режим:  НВ" : "";
    }

    private static double rawMode(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(MODE_KEY, 0.0D);
    }
}
