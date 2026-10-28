package net.ssc14.content.commerce;

public final class VendingButtonIds {
    public static final int FIRST_BUTTON = 0;
    public static final int LAST_BUTTON = 5;

    private VendingButtonIds() { }

    public static int slotForButton(int buttonId) {
        return buttonId >= FIRST_BUTTON && buttonId <= LAST_BUTTON ? buttonId + 1 : -1;
    }
}
