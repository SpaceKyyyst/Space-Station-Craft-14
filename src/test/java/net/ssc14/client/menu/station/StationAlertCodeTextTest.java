package net.ssc14.client.menu.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StationAlertCodeTextTest {
    @Test
    void preservesLegacyAlertCodeLabels() {
        assertEquals("Код:  N/D", StationAlertCodeText.format(0));
        assertEquals("Код:  Зелёный", StationAlertCodeText.format(1));
        assertEquals("Код:  Жёлтый", StationAlertCodeText.format(2));
        assertEquals("Код:  Синий", StationAlertCodeText.format(3));
        assertEquals("Код:  Красный", StationAlertCodeText.format(4));
        assertEquals("Код:  Фиолетовый", StationAlertCodeText.format(5));
        assertEquals("Код:  Гамма", StationAlertCodeText.format(6));
        assertEquals("Код:  Дельта", StationAlertCodeText.format(7));
        assertEquals("Код:  Эпсилон", StationAlertCodeText.format(8));
        assertEquals("Код:  N/D", StationAlertCodeText.format(99));
    }
}
