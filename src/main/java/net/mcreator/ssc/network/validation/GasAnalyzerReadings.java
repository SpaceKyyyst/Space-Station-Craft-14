package net.mcreator.ssc.network.validation;

/** Physical values transported by the gas analyser snapshot, in SI units. */
public record GasAnalyzerReadings(float pressurePa, float temperatureKelvin, float totalMoles, float oxygenMoles, float nitrogenMoles) {
    public GasAnalyzerReadings {
        if (!Float.isFinite(pressurePa) || pressurePa < 0
                || !Float.isFinite(temperatureKelvin) || temperatureKelvin < 1
                || !Float.isFinite(totalMoles) || totalMoles < 0
                || !Float.isFinite(oxygenMoles) || oxygenMoles < 0 || oxygenMoles > totalMoles
                || !Float.isFinite(nitrogenMoles) || nitrogenMoles < 0 || nitrogenMoles > totalMoles) {
            throw new IllegalArgumentException("Invalid gas analyser reading");
        }
    }
}
