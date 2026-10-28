package net.ssc14.core.network;

import java.util.Map;

/** Minimal state contract shared by generated and canonical menus. */
public interface MenuStateAccess {
    Map<String, Object> getMenuState();

    default <T> T getMenuState(int elementType, String name, T defaultValue) {
        try {
            return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
        } catch (ClassCastException ignored) {
            return defaultValue;
        }
    }
}
