package net.ssc14.client.menu;

/** Receives server-authoritative state updates for the currently open menu screen. */
public interface MenuStateReceiver {
    void updateMenuState(int elementType, String name, Object elementState);
}
