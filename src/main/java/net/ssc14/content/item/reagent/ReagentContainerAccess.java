package net.ssc14.content.item.reagent;

import java.util.Map;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/** Boundary between canonical container items and the legacy chemistry runtime. */
public final class ReagentContainerAccess {
    private static volatile ReagentContainerRuntime runtime;

    private ReagentContainerAccess() { }

    public static void install(ReagentContainerRuntime runtime) {
        ReagentContainerAccess.runtime = Objects.requireNonNull(runtime, "runtime");
    }

    public static Map<String, Integer> contents(ItemStack stack) {
        return runtime().contents(stack);
    }

    public static boolean add(ItemStack stack, String reagentId, int amount) {
        return runtime().add(stack, reagentId, amount);
    }

    public static boolean remove(ItemStack stack, String reagentId, int amount) {
        return runtime().remove(stack, reagentId, amount);
    }

    public static int drink(ItemStack stack, int amount, ServerPlayer player) {
        return runtime().drink(stack, amount, player);
    }

    public static int totalAmount(ItemStack stack) {
        return contents(stack).values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int mixedColor(ItemStack stack) {
        return runtime().mixedColor(stack);
    }

    private static ReagentContainerRuntime runtime() {
        ReagentContainerRuntime current = runtime;
        if (current == null) {
            throw new IllegalStateException("Reagent container runtime is not installed");
        }
        return current;
    }
}
