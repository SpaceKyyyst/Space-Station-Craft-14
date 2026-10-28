package net.ssc14.content.menu.machinery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MachineFrameDisplayStateTest {
    @Test
    void preservesSubstationRecipeText() {
        var state = MachineFrameDisplayState.forBoard(
                MachineFrameDisplayState.BoardType.SUBSTATION, true);
        assertEquals("Подстанция", state.title());
        assertEquals("x5 СВ кабель", state.requirement1());
        assertEquals("x5 ВВ кабель", state.requirement2());
        assertEquals("x1 Батарея мал.", state.requirement3());
        assertEquals("x1 Конденсатор", state.requirement4());
        assertTrue(state.hasBoard());
        assertTrue(state.ready());
    }

    @Test
    void preservesEmptyBoardText() {
        var state = MachineFrameDisplayState.forBoard(
                MachineFrameDisplayState.BoardType.NONE, false);
        assertEquals("ВСТАВЬТЕ ПЛАТУ", state.title());
        assertEquals("", state.requirement1());
        assertFalse(state.hasBoard());
        assertFalse(state.ready());
    }

    @Test
    void preservesSmesRecipeText() {
        var state = MachineFrameDisplayState.forBoard(
                MachineFrameDisplayState.BoardType.SMES, false);
        assertEquals("СМЭС", state.title());
        assertEquals("x1 Конденсатор", state.requirement1());
        assertEquals("x10 ВВ кабеля", state.requirement2());
        assertEquals("x4 Батарея мал.", state.requirement3());
        assertEquals("", state.requirement4());
        assertTrue(state.hasBoard());
        assertFalse(state.ready());
    }
}
