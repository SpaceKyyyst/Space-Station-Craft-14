package net.mcreator.ssc.network.validation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Bounds applied before allocating or retaining decoded GUI state. */
public final class PayloadLimits {
    public static final int MAX_STATE_ENTRIES = 256;
    public static final int MAX_KEY_LENGTH = 256;
    public static final int MAX_TEXT_LENGTH = 8192;
    public static final int MAX_TOTAL_TEXT_LENGTH = 32768;

    private PayloadLimits() {
    }

    public static int requireCount(int count, int maximum) {
        if (count < 0 || count > maximum) {
            throw new IllegalArgumentException("Payload entry count exceeds bounds: " + count);
        }
        return count;
    }

    public static String requireText(String text, int maximum) {
        Objects.requireNonNull(text, "Payload text");
        if (text.length() > maximum) {
            throw new IllegalArgumentException("Payload text exceeds " + maximum + " characters");
        }
        return text;
    }

    public static Map<String, Object> snapshot(Map<String, Object> state) {
        Objects.requireNonNull(state, "GUI state");
        requireCount(state.size(), MAX_STATE_ENTRIES);
        Map<String, Object> copy = new LinkedHashMap<>();
        int textLength = 0;
        for (Map.Entry<String, Object> entry : state.entrySet()) {
            String key = requireText(entry.getKey(), MAX_KEY_LENGTH);
            Object value = entry.getValue();
            textLength += key.length();
            if (value instanceof String text) {
                textLength += requireText(text, MAX_TEXT_LENGTH).length();
            } else if (value != null && !(value instanceof Boolean) && !(value instanceof Integer)) {
                throw new IllegalArgumentException("Unsupported GUI state value");
            }
            requireCount(textLength, MAX_TOTAL_TEXT_LENGTH);
            copy.put(key, value);
        }
        // Null selection values are part of the existing GUI state contract.
        return Collections.unmodifiableMap(copy);
    }
}
