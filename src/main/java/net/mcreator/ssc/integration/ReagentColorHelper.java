
package net.mcreator.ssc.integration;

import net.mcreator.ssc.ModReagents;
import net.ssc14.content.item.reagent.ReagentContainerItem;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;

public class ReagentColorHelper {
    private ReagentColorHelper() {}

    public static int getMixedColor(ItemStack stack) {
        if (!(stack.getItem() instanceof ReagentContainerItem)) { return 0xFFFFFF; }
        var reagents = ModReagents.getReagents(stack);
        var colors = new HashMap<String, Integer>();
        reagents.keySet().forEach(id -> ModReagents.getReagent(id)
                .ifPresent(reagent -> colors.put(id, reagent.getColorRgb())));
        return net.ssc14.common.chemistry.ReagentColorMixer.mix(reagents, colors);
    }

    public static boolean hasReagents(ItemStack stack) {
        if (!(stack.getItem() instanceof ReagentContainerItem)) { return false; }
        return !ModReagents.getReagents(stack).isEmpty();
    }
}
