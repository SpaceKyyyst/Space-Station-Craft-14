package net.ssc14.content.item.reagent;

import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/** Runtime port required by canonical reagent container items. */
public interface ReagentContainerRuntime {
    Map<String, Integer> contents(ItemStack stack);

    boolean add(ItemStack stack, String reagentId, int amount);

    int drink(ItemStack stack, int amount, ServerPlayer player);

    int mixedColor(ItemStack stack);
}
