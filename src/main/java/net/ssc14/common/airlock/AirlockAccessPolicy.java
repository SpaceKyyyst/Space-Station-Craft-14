package net.ssc14.common.airlock;

import java.util.Map;

/** Stable departmental access mapping for departmental airlock registry paths. */
public final class AirlockAccessPolicy {
    private static final Map<String, String> ACCESS_BY_BLOCK = Map.ofEntries(
            Map.entry("tech_airlock_d_1", "Technical"),
            Map.entry("atmospherics_airlock_d_1", "Atmos"),
            Map.entry("cargo_airlock_d_1", "Supply_Deportament"),
            Map.entry("chemistry_airlock_d_1", "Chemistry"),
            Map.entry("command_airlock_d_1", "Command"),
            Map.entry("engineering_airlock_d_1", "Ingeneer"),
            Map.entry("hydroponics_airlock_d_1", "Gidroponic"),
            Map.entry("medical_airlock_d_1", "Medical"),
            Map.entry("salvage_airlock_d_1", "Utilizat"),
            Map.entry("science_airlock_d_1", "Scientist"),
            Map.entry("security_airlock_d_1", "Security"),
            Map.entry("virology_airlock_d_1", "Medical"));

    private AirlockAccessPolicy() { }

    public static String accessKeyFor(String registryPath) {
        return ACCESS_BY_BLOCK.get(registryPath);
    }
}
