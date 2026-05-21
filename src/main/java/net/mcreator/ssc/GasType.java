
package net.mcreator.ssc;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * Реестр всех газов в моде.
 * Индекс в массиве = ordinal() этого энума.
 */
public enum GasType {
    OXYGEN("oxygen", 0.032f, 29.4f),           // O2, молярная масса, теплоёмкость
    NITROGEN("nitrogen", 0.028f, 29.1f),        // N2
    CARBON_DIOXIDE("carbon_dioxide", 0.044f, 37.1f), // CO2
    NITROUS_OXIDE("nitrous_oxide", 0.044f, 38.5f),   // N2O
    PLASMA("plasma", 0.064f, 45.0f),            // Плазма (горючая)
    AMMONIA("ammonia", 0.017f, 35.6f),          // NH3
    TRITIUM("tritium", 0.048f, 42.0f),          // Тритий
    FREON("freon", 0.104f, 85.0f),              // Фреон (охлаждающий)
    HELIUM("helium", 0.004f, 20.8f),            // He
    WATER_VAPOR("water_vapor", 0.018f, 33.6f);  // H2O пар

    public static final int COUNT = values().length; // = 10

    private final String name;
    private final float molarMass;      // кг/моль
    private final float heatCapacity;   // Дж/(моль·К)

    // Быстрый доступ по индексу
    private static final GasType[] BY_INDEX = values();
    // Быстрый доступ по строковому ID
    private static final Map<String, GasType> BY_NAME = new HashMap<>();

    static {
        for (GasType gas : values()) {
            BY_NAME.put(gas.name, gas);
        }
    }

    GasType(String name, float molarMass, float heatCapacity) {
        this.name = name;
        this.molarMass = molarMass;
        this.heatCapacity = heatCapacity;
    }

    public static GasType byIndex(int index) {
        if (index < 0 || index >= COUNT) return null;
        return BY_INDEX[index];
    }

    public static GasType byName(String name) {
        return BY_NAME.get(name);
    }

    public String getName() { return name; }
    public float getMolarMass() { return molarMass; }
    public float getHeatCapacity() { return heatCapacity; }

    /**
     * Проверка: является ли газ горючим (для реакций)
     */
    public boolean isFlammable() {
        return this == PLASMA || this == TRITIUM || this == AMMONIA;
    }

    /**
     * Проверка: является ли газ окислителем
     */
    public boolean isOxidizer() {
        return this == OXYGEN;
    }
}
