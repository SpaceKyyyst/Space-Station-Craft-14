package net.ssc14.content.item.reagent;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.common.chemistry.ReagentAmounts;

import java.util.LinkedHashMap;
import java.util.Map;

/** Canonical storage operations for reagent container contents. */
public final class ReagentContainerStorage {
    private ReagentContainerStorage() {
    }

    public static Map<String, Integer> contents(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return ReagentContentsCodec.read(data == null ? new net.minecraft.nbt.CompoundTag() : data.copyTag());
    }

    public static boolean add(ItemStack stack, String reagentId, int amount) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ReagentContainerItem container)
                || !ReagentAmounts.canAdd(contents(stack), container.getMaxCapacity(), reagentId, amount)) {
            return false;
        }
        Map<String, Integer> next = new LinkedHashMap<>(contents(stack));
        next.put(reagentId, next.getOrDefault(reagentId, 0) + amount);
        write(stack, next);
        return true;
    }

    public static boolean remove(ItemStack stack, String reagentId, int amount) {
        if (stack.isEmpty() || reagentId == null || amount <= 0) {
            return false;
        }
        Map<String, Integer> next = new LinkedHashMap<>(contents(stack));
        int stored = next.getOrDefault(reagentId, 0);
        if (stored < amount) {
            return false;
        }
        if (stored == amount) {
            next.remove(reagentId);
        } else {
            next.put(reagentId, stored - amount);
        }
        write(stack, next);
        return true;
    }

    private static void write(ItemStack stack, Map<String, Integer> contents) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                root -> root.put(ReagentContentsCodec.NBT_KEY, ReagentContentsCodec.write(contents)));
    }
}
