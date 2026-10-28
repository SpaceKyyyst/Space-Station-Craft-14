package net.ssc14.common.airlock;

/** Construction graph from Corvax airlock.yml; persisted values 0..2 remain compatible. */
public final class AirlockProtection {
    public static final int BARE = 0, STEEL_ATTACHED = 1, STEEL_WELDED = 2,
            PLASTEEL_ATTACHED = 3, PLASTEEL_WELDED = 4, GRILLE = 5,
            OUTER_ATTACHED = 6, OUTER_WELDED = 7;
    public enum Tool { STEEL, PLASTEEL, RODS, WELDER, CROWBAR, CUTTERS }
    public record Transition(int target, int ticks, Tool consumed, Tool returned, boolean shock) { }
    private AirlockProtection() { }

    public static Transition transition(int state, Tool tool) {
        return switch (state) {
            case BARE -> switch (tool) {
                case STEEL -> step(STEEL_ATTACHED, 40, tool, null);
                case PLASTEEL -> step(PLASTEEL_ATTACHED, 40, tool, null);
                default -> null;
            };
            case STEEL_ATTACHED -> switch (tool) {
                case CROWBAR -> step(BARE, 80, null, Tool.STEEL);
                case WELDER -> step(STEEL_WELDED, 60, null, null);
                default -> null;
            };
            case STEEL_WELDED -> tool == Tool.WELDER ? step(STEEL_ATTACHED, 200, null, null) : null;
            case PLASTEEL_ATTACHED -> switch (tool) {
                case CROWBAR -> step(BARE, 80, null, Tool.PLASTEEL);
                case WELDER -> step(PLASTEEL_WELDED, 100, null, null);
                default -> null;
            };
            case PLASTEEL_WELDED -> switch (tool) {
                case WELDER -> step(PLASTEEL_ATTACHED, 300, null, null);
                case RODS -> step(GRILLE, 20, tool, null);
                default -> null;
            };
            case GRILLE -> switch (tool) {
                case CUTTERS -> new Transition(PLASTEEL_WELDED, 10, null, Tool.RODS, true);
                case PLASTEEL -> step(OUTER_ATTACHED, 40, tool, null);
                default -> null;
            };
            case OUTER_ATTACHED -> switch (tool) {
                case CROWBAR -> step(GRILLE, 80, null, Tool.PLASTEEL);
                case WELDER -> step(OUTER_WELDED, 100, null, null);
                default -> null;
            };
            case OUTER_WELDED -> tool == Tool.WELDER ? step(OUTER_ATTACHED, 300, null, null) : null;
            default -> null;
        };
    }

    private static Transition step(int target, int ticks, Tool consumed, Tool returned) {
        return new Transition(target, ticks, consumed, returned, false);
    }
}
