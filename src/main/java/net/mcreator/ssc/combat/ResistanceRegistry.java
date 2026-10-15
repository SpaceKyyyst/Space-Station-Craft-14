package net.mcreator.ssc.combat;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/** Resistance rules independent of Minecraft inventory and event APIs. */
public final class ResistanceRegistry {
    private static final double MIN_RESISTANCE = 0.0;
    private static final double MAX_RESISTANCE = 1.0;

    private record Key(String itemId, String damageType) {
        private Key {
            Objects.requireNonNull(itemId, "itemId");
            Objects.requireNonNull(damageType, "damageType");
        }
    }

    private final Map<Key, Double> resistances = new ConcurrentHashMap<>();

    public void register(String itemId, String damageType, double resistance) {
        if (!Double.isFinite(resistance)) {
            throw new IllegalArgumentException("Resistance must be finite: " + resistance);
        }
        resistances.put(new Key(itemId, damageType),
                Math.min(Math.max(resistance, MIN_RESISTANCE), MAX_RESISTANCE));
    }

    public double getResistance(String itemId, String damageType) {
        return resistances.getOrDefault(new Key(itemId, damageType), MIN_RESISTANCE);
    }

    /** Apply one equipped item's rule; subsequent items consume the remaining damage. */
    public float apply(String itemId, String damageType, float incomingDamage) {
        float resistance = (float) getResistance(itemId, damageType);
        return Math.max(incomingDamage * (1.0f - resistance), 0.0f);
    }
}
