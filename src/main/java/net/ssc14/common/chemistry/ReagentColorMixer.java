package net.ssc14.common.chemistry;

import java.util.Map;

/** Pure weighted RGB mixing for reagent contents. */
public final class ReagentColorMixer {
    private static final int EMPTY_COLOR = 0xFFFFFF;

    private ReagentColorMixer() { }

    public static int mix(Map<String, Integer> amounts, Map<String, Integer> colors) {
        if (amounts == null || amounts.isEmpty()) return EMPTY_COLOR;
        int total = amounts.values().stream().mapToInt(Integer::intValue).sum();
        if (total <= 0) return EMPTY_COLOR;

        double red = 0;
        double green = 0;
        double blue = 0;
        for (var entry : amounts.entrySet()) {
            Integer color = colors.get(entry.getKey());
            if (color == null || entry.getValue() == null || entry.getValue() <= 0) continue;
            double ratio = (double) entry.getValue() / total;
            red += ((color >> 16) & 0xFF) * ratio;
            green += ((color >> 8) & 0xFF) * ratio;
            blue += (color & 0xFF) * ratio;
        }
        return ((int) red << 16) | ((int) green << 8) | (int) blue;
    }
}
