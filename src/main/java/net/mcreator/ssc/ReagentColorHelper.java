
package net.mcreator.ssc;

import net.mcreator.ssc.item.ReagentContainerItem;
import net.minecraft.world.item.ItemStack;
import java.util.Map;

public class ReagentColorHelper {
    private ReagentColorHelper() {}

    public static int getMixedColor(ItemStack stack) {
        if (!(stack.getItem() instanceof ReagentContainerItem)) { return 0xFFFFFF; }
        Map<String, Integer> reagents = ModReagents.getReagents(stack);
        if (reagents.isEmpty()) { return 0xFFFFFF; }

        int totalVolume = reagents.values().stream().mapToInt(Integer::intValue).sum();
        if (totalVolume == 0) { return 0xFFFFFF; }

        float[] rgb = new float[]{0, 0, 0};
        reagents.forEach((reagentId, volume) -> {
            float ratio = (float) volume / totalVolume;
            ModReagents.getReagent(reagentId).ifPresent(reagent -> {
                int color = reagent.getColorRgb();
                rgb[0] += ((color >> 16) & 0xFF) * ratio;
                rgb[1] += ((color >> 8) & 0xFF) * ratio;
                rgb[2] += (color & 0xFF) * ratio;
            });
        });
        return ((int) rgb[0] << 16) | ((int) rgb[1] << 8) | (int) rgb[2];
    }

    public static boolean hasReagents(ItemStack stack) {
        if (!(stack.getItem() instanceof ReagentContainerItem)) { return false; }
        return !ModReagents.getReagents(stack).isEmpty();
    }
}
