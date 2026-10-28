package net.ssc14.core.network;

import java.util.Set;

/** Client-editable widgets; server-owned labels and network connection IDs are excluded. */
public final class MenuStateRules {
    private static final Set<String> ACCESS_FIELDS = Set.of(
            "Atmos", "Bar", "Blue_Sh", "Brig", "Capitan", "CE", "Chemistry", "Church", "Cleaner",
            "CMO", "Command", "Crio", "Detective", "Gidroponic", "gun_room", "HoP", "HoS",
            "Ingeneer", "Kitchen", "Medical", "Out", "PNT", "Qm", "RD", "Scientist", "Security",
            "Service", "Supply_Deportament", "Teatre", "Technical", "Uridic", "Utilizat");

    private MenuStateRules() {
    }

    public static void requireValid(int type, String name, Object state) {
        PayloadLimits.requireText(name, PayloadLimits.MAX_KEY_LENGTH);
        boolean valid = switch (type) {
            case 0 -> state instanceof String text && text.length() <= PayloadLimits.MAX_TEXT_LENGTH;
            case 1 -> state instanceof Boolean;
            case 2 -> state instanceof Number number && Double.isFinite(number.doubleValue());
            default -> false;
        };
        if (!valid) {
            throw new IllegalArgumentException("Invalid menu state type or value");
        }
    }

    public static boolean acceptsClientField(String menuClass, int type, String name) {
        return switch (menuClass) {
            case "AccessConfigMENUMenu" -> type == 1 && ACCESS_FIELDS.contains(name);
            case "IDcodeMenu" -> type == 1 && (ACCESS_FIELDS.contains(name) || name.equals("Job_Name_Readact"))
                    || type == 0 && name.equals("Job_Name");
            case "DECALScolorGUIMenu" -> type == 0 && Set.of("R", "G", "B").contains(name);
            default -> false;
        };
    }
}
