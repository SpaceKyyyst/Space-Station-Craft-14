package net.ssc14.content.menu.decal;

/** Preserves the generated RGB parsing and range contract. */
public final class DecalColorInput {
    private static final double MIN_COMPONENT = 0;
    private static final double MAX_COMPONENT = 255;

    private DecalColorInput() { }

    public static double parse(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (RuntimeException ignored) {
            return 0;
        }
    }

    public static boolean isValid(String red, String green, String blue) {
        return inRange(parse(red)) && inRange(parse(green)) && inRange(parse(blue));
    }

    private static boolean inRange(double value) {
        return value >= MIN_COMPONENT && value <= MAX_COMPONENT;
    }
}
