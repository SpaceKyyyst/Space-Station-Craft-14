package net.ssc14.content.commerce.cargo;

public final class CargoOrderButtonIds {
    public static final int FIRST_TAB_BUTTON = 0;
    public static final int LAST_TAB_BUTTON = 14;
    public static final int FIRST_ITEM_BUTTON = 100;
    public static final int LAST_ITEM_BUTTON = 109;

    private CargoOrderButtonIds() {
    }

    public static int tabForButton(int buttonId) {
        return buttonId >= FIRST_TAB_BUTTON && buttonId <= LAST_TAB_BUTTON ? buttonId + 1 : -1;
    }

    public static int itemIndexForButton(int buttonId) {
        return buttonId >= FIRST_ITEM_BUTTON && buttonId <= LAST_ITEM_BUTTON
                ? buttonId - FIRST_ITEM_BUTTON
                : -1;
    }
}
