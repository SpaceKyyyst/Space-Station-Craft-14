package net.ssc14.content.menu.food;

/** Stable button identifiers used by the microwave menu network contract. */
public final class MicrowaveButtonIds {
    public static final int START = 8;

    private static final int[] TIMER_SECONDS = {1, 5, 10, 15, 20, 25, 0, 30};

    private MicrowaveButtonIds() { }

    public static int timerSeconds(int buttonId) {
        return buttonId >= 0 && buttonId < TIMER_SECONDS.length
                ? TIMER_SECONDS[buttonId]
                : -1;
    }
}
