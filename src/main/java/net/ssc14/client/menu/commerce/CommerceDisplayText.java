package net.ssc14.client.menu.commerce;

/** Stable text formatting for commerce screens. */
public final class CommerceDisplayText {
    private CommerceDisplayText() { }

    public static String currency(double amount) {
        return "$:  " + Math.round(amount);
    }
}
