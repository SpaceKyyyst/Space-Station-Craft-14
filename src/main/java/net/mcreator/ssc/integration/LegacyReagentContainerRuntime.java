package net.mcreator.ssc.integration;

import java.util.Map;
import net.mcreator.ssc.ModReagents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.reagent.ReagentContainerRuntime;

/** Adapter retained until the chemistry runtime moves into the canonical namespace. */
public final class LegacyReagentContainerRuntime implements ReagentContainerRuntime {
    public static final LegacyReagentContainerRuntime INSTANCE =
            new LegacyReagentContainerRuntime();

    private LegacyReagentContainerRuntime() { }

    @Override
    public Map<String, Integer> contents(ItemStack stack) {
        return ModReagents.getReagents(stack);
    }

    @Override
    public boolean add(ItemStack stack, String reagentId, int amount) {
        return ModReagents.addReagent(stack, reagentId, amount);
    }

    @Override
    public boolean remove(ItemStack stack, String reagentId, int amount) {
        return ModReagents.removeReagent(stack, reagentId, amount);
    }

    @Override
    public int drink(ItemStack stack, int amount, ServerPlayer player) {
        return ModReagents.drinkReagents(stack, amount, player);
    }

    @Override
    public int mixedColor(ItemStack stack) {
        return ReagentColorHelper.getMixedColor(stack);
    }
}
