package net.ssc14.client.menu.station;

/** Preserves the communication-console text contract for station alert codes. */
public final class StationAlertCodeText {
    private StationAlertCodeText() { }

    public static String format(int code) {
        String label = switch (code) {
            case 1 -> "Зелёный";
            case 2 -> "Жёлтый";
            case 3 -> "Синий";
            case 4 -> "Красный";
            case 5 -> "Фиолетовый";
            case 6 -> "Гамма";
            case 7 -> "Дельта";
            case 8 -> "Эпсилон";
            default -> "N/D";
        };
        return "Код:  " + label;
    }
}
