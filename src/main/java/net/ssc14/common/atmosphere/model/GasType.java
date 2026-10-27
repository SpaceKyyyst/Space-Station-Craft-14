package net.ssc14.common.atmosphere.model;

import java.util.HashMap;
import java.util.Map;

/** Stable gas catalog. Declaration order is part of the persistence and network contract. */
public enum GasType {
    OXYGEN("oxygen", 0.032f, 29.4f),
    NITROGEN("nitrogen", 0.028f, 29.1f),
    CARBON_DIOXIDE("carbon_dioxide", 0.044f, 37.1f),
    NITROUS_OXIDE("nitrous_oxide", 0.044f, 38.5f),
    PLASMA("plasma", 0.064f, 45.0f),
    AMMONIA("ammonia", 0.017f, 35.6f),
    TRITIUM("tritium", 0.048f, 42.0f),
    FREON("freon", 0.104f, 85.0f),
    HELIUM("helium", 0.004f, 20.8f),
    WATER_VAPOR("water_vapor", 0.018f, 33.6f);

    private static final GasType[] BY_INDEX = values();
    private static final Map<String, GasType> BY_NAME = createNameIndex();

    public static final int COUNT = BY_INDEX.length;

    private final String name;
    private final float molarMass;
    private final float heatCapacity;

    GasType(String name, float molarMass, float heatCapacity) {
        this.name = name;
        this.molarMass = molarMass;
        this.heatCapacity = heatCapacity;
    }

    public static GasType byIndex(int index) {
        return index >= 0 && index < COUNT ? BY_INDEX[index] : null;
    }

    public static GasType byName(String name) {
        return name == null ? null : BY_NAME.get(name);
    }

    public String getName() {
        return name;
    }

    public float getMolarMass() {
        return molarMass;
    }

    public float getHeatCapacity() {
        return heatCapacity;
    }

    public boolean isFlammable() {
        return this == PLASMA || this == TRITIUM || this == AMMONIA;
    }

    public boolean isOxidizer() {
        return this == OXYGEN;
    }

    private static Map<String, GasType> createNameIndex() {
        Map<String, GasType> byName = new HashMap<>();
        for (GasType gas : values()) {
            byName.put(gas.name, gas);
        }
        return Map.copyOf(byName);
    }
}
