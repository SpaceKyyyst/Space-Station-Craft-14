package net.ssc14.common.atmosphere.reaction;

import java.util.Objects;
import net.ssc14.common.atmosphere.model.GasType;

/** Immutable reaction definition. Cell arrays are mutated only after a complete valid calculation. */
public record GasReaction(
        String id,
        boolean[] requiredGases,
        float minTemperatureK,
        float maxTemperatureK,
        float[] consumptionPerTick,
        float[] productionPerTick,
        float heatReleaseJoules,
        float activationThreshold
) {
    private static final float MIN_TEMPERATURE_K = 1f;

    public GasReaction {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(requiredGases, "requiredGases");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Reaction ID must not be blank");
        }
        if (requiredGases.length != GasType.COUNT) {
            throw new IllegalArgumentException("Required gas flags must have length " + GasType.COUNT);
        }
        requireAmounts(consumptionPerTick, "consumptionPerTick");
        requireAmounts(productionPerTick, "productionPerTick");
        if (!Float.isFinite(minTemperatureK) || !Float.isFinite(maxTemperatureK)
                || minTemperatureK < MIN_TEMPERATURE_K || maxTemperatureK < minTemperatureK) {
            throw new IllegalArgumentException("Invalid reaction temperature range");
        }
        if (!Float.isFinite(heatReleaseJoules)) {
            throw new IllegalArgumentException("Heat release must be finite");
        }
        if (!Float.isFinite(activationThreshold) || activationThreshold < 0f) {
            throw new IllegalArgumentException("Activation threshold must be finite and non-negative");
        }
        requiredGases = requiredGases.clone();
        consumptionPerTick = consumptionPerTick.clone();
        productionPerTick = productionPerTick.clone();
    }

    @Override
    public boolean[] requiredGases() {
        return requiredGases.clone();
    }

    @Override
    public float[] consumptionPerTick() {
        return consumptionPerTick.clone();
    }

    @Override
    public float[] productionPerTick() {
        return productionPerTick.clone();
    }

    /** Preliminary activation check; reagent sufficiency is checked by apply. */
    public boolean canOccur(float[] moles, float temperatureK) {
        requireShape(moles, "moles");
        if (!Float.isFinite(temperatureK) || temperatureK < minTemperatureK || temperatureK > maxTemperatureK) {
            return false;
        }
        double requiredMoles = 0;
        for (int i = 0; i < GasType.COUNT; i++) {
            if (!Float.isFinite(moles[i]) || moles[i] < 0f) {
                return false;
            }
            if (requiredGases[i]) {
                if (moles[i] <= 0f) {
                    return false;
                }
                requiredMoles += moles[i];
            }
        }
        return requiredMoles >= activationThreshold;
    }

    /**
     * Applies stoichiometry and heat in O(G) time and O(G) temporary space.
     * Invalid cell numbers, insufficient reagents and overflow return false without mutation.
     * Invalid array shapes are programming errors and throw IllegalArgumentException.
     */
    public boolean apply(float[] moles, float[] temperatureRef) {
        Objects.requireNonNull(temperatureRef, "temperatureRef");
        if (temperatureRef.length == 0 || temperatureRef == moles) {
            throw new IllegalArgumentException("Temperature requires a separate, non-empty array");
        }
        if (!canOccur(moles, temperatureRef[0])) {
            return false;
        }
        float[] nextMoles = new float[GasType.COUNT];
        double heatCapacity = 0;
        for (int i = 0; i < GasType.COUNT; i++) {
            if (consumptionPerTick[i] > moles[i]) {
                return false;
            }
            nextMoles[i] = moles[i] - consumptionPerTick[i] + productionPerTick[i];
            if (!Float.isFinite(nextMoles[i])) {
                return false;
            }
            heatCapacity += (double) nextMoles[i] * GasType.byIndex(i).getHeatCapacity();
        }
        float nextTemperature = temperatureRef[0];
        if (heatCapacity > 0) {
            double calculatedTemperature = temperatureRef[0] + heatReleaseJoules / heatCapacity;
            nextTemperature = (float) Math.max(MIN_TEMPERATURE_K, calculatedTemperature);
            if (!Float.isFinite(nextTemperature)) {
                return false;
            }
        }
        System.arraycopy(nextMoles, 0, moles, 0, GasType.COUNT);
        temperatureRef[0] = nextTemperature;
        return true;
    }

    public static GasReaction create(String id, boolean[] required, float minT, float maxT,
                                     float[] consume, float[] produce, float heat, float threshold) {
        return new GasReaction(id, required, minT, maxT, consume, produce, heat, threshold);
    }

    private static void requireShape(float[] amounts, String name) {
        Objects.requireNonNull(amounts, name);
        if (amounts.length != GasType.COUNT) {
            throw new IllegalArgumentException(name + " must have length " + GasType.COUNT);
        }
    }

    private static void requireAmounts(float[] amounts, String name) {
        requireShape(amounts, name);
        for (float amount : amounts) {
            if (!Float.isFinite(amount) || amount < 0f) {
                throw new IllegalArgumentException(name + " must contain finite, non-negative amounts");
            }
        }
    }
}
