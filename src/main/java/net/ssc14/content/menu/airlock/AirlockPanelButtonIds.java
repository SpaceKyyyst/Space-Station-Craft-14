package net.ssc14.content.menu.airlock;

/** Stable mapping between the eight panel button IDs and one-based wire numbers. */
public final class AirlockPanelButtonIds {
    public static final int FIRST_BUTTON = 0;
    public static final int LAST_BUTTON = 7;

    private AirlockPanelButtonIds() {
    }

    public static int wireForButton(int buttonId) {
        return buttonId >= FIRST_BUTTON && buttonId <= LAST_BUTTON ? buttonId + 1 : -1;
    }
}
