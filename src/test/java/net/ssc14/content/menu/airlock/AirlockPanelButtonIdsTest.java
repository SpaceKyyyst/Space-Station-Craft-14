package net.ssc14.content.menu.airlock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AirlockPanelButtonIdsTest {
    @Test
    void preservesEightButtonWireMapping() {
        for (int button = 0; button < 8; button++) {
            assertEquals(button + 1, AirlockPanelButtonIds.wireForButton(button));
        }
        assertEquals(-1, AirlockPanelButtonIds.wireForButton(-1));
        assertEquals(-1, AirlockPanelButtonIds.wireForButton(8));
    }
}
