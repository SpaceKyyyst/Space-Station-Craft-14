package net.ssc14.content.menu.food;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MicrowaveButtonIdsTest {
    @Test
    void preservesLegacyTimerButtonContract() {
        assertEquals(1, MicrowaveButtonIds.timerSeconds(0));
        assertEquals(5, MicrowaveButtonIds.timerSeconds(1));
        assertEquals(10, MicrowaveButtonIds.timerSeconds(2));
        assertEquals(15, MicrowaveButtonIds.timerSeconds(3));
        assertEquals(20, MicrowaveButtonIds.timerSeconds(4));
        assertEquals(25, MicrowaveButtonIds.timerSeconds(5));
        assertEquals(0, MicrowaveButtonIds.timerSeconds(6));
        assertEquals(30, MicrowaveButtonIds.timerSeconds(7));
        assertEquals(-1, MicrowaveButtonIds.timerSeconds(MicrowaveButtonIds.START));
        assertEquals(-1, MicrowaveButtonIds.timerSeconds(99));
    }
}
