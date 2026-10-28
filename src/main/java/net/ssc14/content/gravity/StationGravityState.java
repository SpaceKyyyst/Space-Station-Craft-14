package net.ssc14.content.gravity;

/** Process-wide station gravity flag retained for compatibility with the current single-station simulation. */
public final class StationGravityState {
    private static volatile boolean enabled;

    private StationGravityState() { }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }
}
