package net.ssc14.content.network;

/** Stable button-ID partition for the network configurator menu. */
public final class NetworkConfigButtonIds {
    public static final int SIDE_SIZE = 8;
    private static final int FIRST_TRIGGER = 0;
    private static final int FIRST_ACTION = SIDE_SIZE;
    private static final int LAST_ACTION = FIRST_ACTION + SIDE_SIZE - 1;

    private NetworkConfigButtonIds() { }

    public static boolean isValid(int buttonId) {
        return buttonId >= FIRST_TRIGGER && buttonId <= LAST_ACTION;
    }

    public static boolean isTrigger(int buttonId) {
        return buttonId >= FIRST_TRIGGER && buttonId < FIRST_ACTION;
    }

    public static int triggerIndex(int buttonId) {
        return buttonId;
    }

    public static int actionIndex(int buttonId) {
        return buttonId - FIRST_ACTION;
    }
}
