package net.ssc14.client.menu.pda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PassengerPdaPresentationTest {
    @Test
    void preservesTabSelectionContract() {
        var state = new PassengerPdaPresentation(
                false, false, false, 2, "N/D", "N/D", "", -1);
        assertFalse(state.mainTab());
        assertTrue(state.secondTab());
        assertFalse(state.thirdTab());
    }

    @Test
    void preservesAlertCodeColors() {
        assertEquals(-1, PassengerPdaPresentation.alertForCode(0).color());
        assertEquals("N/D", PassengerPdaPresentation.alertForCode(0).text());
        assertEquals(-14585048, PassengerPdaPresentation.alertForCode(1).color());
        assertEquals(-12316287, PassengerPdaPresentation.alertForCode(8).color());
        assertEquals("", PassengerPdaPresentation.alertForCode(9).text());
    }
}
